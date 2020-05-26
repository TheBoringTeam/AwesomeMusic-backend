package com.music.awesomemusic.services


import com.music.awesomemusic.persistence.domain.AwesomeAccount
import com.music.awesomemusic.persistence.dto.request.AccountSignUpForm
import com.music.awesomemusic.repositories.IAccountRepository
import com.music.awesomemusic.utils.exceptions.basic.ResourceNotFoundException
import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Lazy
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

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
    @Lazy
    lateinit var passwordEncoder: PasswordEncoder


    fun findByUsername(username: String): AwesomeAccount {
        return accountRepository.findByUsername(username).orElseThrow {
            UsernameNotFoundException("Username was not found")
        }
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

    fun findByEmail(email: String): AwesomeAccount {
        return accountRepository.findByEmail(email).orElseThrow { ResourceNotFoundException("User with email [${email}] doesn't exist") }
    }
}