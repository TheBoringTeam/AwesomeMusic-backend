package com.music.awesomemusic.services


import com.music.awesomemusic.persistence.domain.AwesomeAccount
import com.music.awesomemusic.persistence.domain.Education
import com.music.awesomemusic.persistence.domain.Gender
import com.music.awesomemusic.persistence.dto.request.AccountSignUpForm
import com.music.awesomemusic.persistence.dto.request.UpdateAccountForm
import com.music.awesomemusic.repositories.IAccountRepository
import com.music.awesomemusic.utils.exceptions.basic.ResourceNotFoundException
import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Lazy
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * Service for account management.
 *
 */
@Service
class AccountService {

    private val _logger = Logger.getLogger(AccountService::class.java)

    @Autowired
    lateinit var accountRepository: IAccountRepository

    @Autowired
    lateinit var roleService: RoleService

    @Autowired
    lateinit var informationService: InformationService

    @Autowired
    @Lazy
    lateinit var passwordEncoder: PasswordEncoder


    fun findByUsername(username: String): AwesomeAccount {
        return accountRepository.findByUsername(username).orElseThrow {
            UsernameNotFoundException("Username was not found")
        }
    }

    fun findByUUID(uuid: UUID): AwesomeAccount {
        return accountRepository.findByUuid(uuid).orElseThrow { ResourceNotFoundException("Account with this UUID was not found") }
    }

    fun createAccount(userFormDto: AccountSignUpForm): AwesomeAccount {
        try {
            _logger.debug("Creating user was started")

            val account = AwesomeAccount(userFormDto.username, passwordEncoder.encode(userFormDto.password), userFormDto.email,
                    userFormDto.username, userFormDto.isCollective)

            accountRepository.save(account)

            _logger.debug("User ${account.username} was successfully created")
            return account
        } catch (e: Exception) {
            _logger.error("Error while creating a new user: ${e.message}")
            throw e
        }
    }

    fun saveAccount(account: AwesomeAccount) {
        accountRepository.save(account)
    }

    fun existsByEmail(email: String): Boolean {
        return accountRepository.existsByEmail(email)
    }

    fun existsByUsername(username: String): Boolean {
        return accountRepository.existsByUsername(username)
    }

    fun setPassword(account: AwesomeAccount, newPassword: String) {
        account.password = passwordEncoder.encode(newPassword)
        saveAccount(account)
    }

    fun isPasswordEquals(password: String, account: AwesomeAccount): Boolean {
        return passwordEncoder.matches(password, account.password)
    }

    fun updateAll(updateForm: UpdateAccountForm, account: AwesomeAccount): AwesomeAccount {
        updateForm.biography?.let {
            account.biography = it
        }

        updateForm.country?.let {
            account.country = informationService.findCountryByCode(it)
        }

        updateForm.language?.let {
            account.language = informationService.findLanguageByCode(it)
        }

        updateForm.gender?.let {
            account.gender = Gender.valueOf(it.toUpperCase())
        }

        updateForm.education?.let {
            account.education = Education.valueOf(it.toUpperCase())
        }

        updateForm.name?.let {
            account.name = it
        }

        updateForm.birthday?.let {
            account.birthday = LocalDate.parse(it, DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        }

        updateForm.deathday?.let {
            account.deathDay = LocalDate.parse(it, DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        }

        saveAccount(account)
        return account
    }

    fun findByEmail(email: String): AwesomeAccount {
        return accountRepository.findByEmail(email).orElseThrow { ResourceNotFoundException("User with email [${email}] doesn't exist") }
    }
}