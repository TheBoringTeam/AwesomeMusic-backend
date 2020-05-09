package com.music.awesomemusic.services

import com.music.awesomemusic.persistence.domain.AwesomeAccount
import com.music.awesomemusic.persistence.domain.EmailVerificationToken
import com.music.awesomemusic.persistence.dto.request.UserRegistrationForm
import com.music.awesomemusic.repositories.IAccountRepository
import com.music.awesomemusic.repositories.IEmailTokenRepository
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
    lateinit var emailTokenRepository: IEmailTokenRepository

    @Autowired
    @Lazy
    lateinit var passwordEncoder: PasswordEncoder


    fun findByUsername(username: String): AwesomeAccount {
        return accountRepository.findByUsername(username).orElseThrow {
            UsernameNotFoundException("Username was not found")
        }
    }

    fun createAccount(userFormDto: UserRegistrationForm): AwesomeAccount {
        try {
            _logger.debug("Creating user was started")

            val role = roleService.findByName("USER")

            val account = AwesomeAccount(userFormDto.username, passwordEncoder.encode(userFormDto.password), userFormDto.email,
                    userFormDto.username, userFormDto.isCollective)

            // TODO : May be some optimization here (IDK rly)
            // add role to new user
            account.addRole(role)

            accountRepository.save(account)

            _logger.debug("User ${account.username} was successfully created")
            return account
        } catch (e: Exception) {
            _logger.error("Error while creating a new user: ${e.message}")
            throw e
        }
    }

    fun createEmailVerificationToken(account: AwesomeAccount, token: String) {
        val verificationToken = EmailVerificationToken(token, account)
        emailTokenRepository.save(verificationToken)
    }

    fun saveAccount(account: AwesomeAccount) {
        accountRepository.save(account)
    }

    fun getVerificationToken(token: String): EmailVerificationToken? {
        return emailTokenRepository.findByToken(token)
    }

    fun existsByEmail(email: String): Boolean {
        return accountRepository.existsByEmail(email)
    }

    fun existsByUsername(username: String): Boolean {
        return accountRepository.existsByUsername(username)
    }
}