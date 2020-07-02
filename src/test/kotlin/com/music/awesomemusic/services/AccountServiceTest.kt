package com.music.awesomemusic.services

import com.music.awesomemusic.persistence.domain.AwesomeAccount
import com.music.awesomemusic.persistence.domain.TokenType
import com.music.awesomemusic.persistence.domain.VerificationToken
import com.music.awesomemusic.persistence.dto.request.AccountSignUpForm
import com.music.awesomemusic.repositories.IAccountRepository
import com.music.awesomemusic.repositories.ITokenRepository
import com.music.awesomemusic.security.tokens.JwtTokenProvider
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.test.context.junit.jupiter.SpringExtension


@SpringBootTest
@ExtendWith(SpringExtension::class)
class AccountServiceTest {

    @Autowired
    lateinit var accountService: AccountService

    @Autowired
    lateinit var accountRepository: IAccountRepository

    @Autowired
    lateinit var tokenRepository: ITokenRepository

    @Autowired
    private lateinit var tokenProvider: JwtTokenProvider

    @Autowired
    lateinit var tokenService: TokenService


    @BeforeEach
    fun init() {
        tokenRepository.deleteAll()
        accountRepository.deleteAll()
    }


    @Test
    fun contextLoads() {
    }


    @Nested
    inner class CreateAccount {

        @Test
        fun `check if database unique values when create user`() {
            val allAccounts = accountRepository.findAll()
            val account = AwesomeAccount("testUsername", "12125125",
                    "test@mail.com", "some_name", false)

            accountService.saveAccount(account)

            val createdAccount = accountRepository.findById(1)
            assertEquals(0, allAccounts.count())

            assertNotNull(createdAccount)
        }


        @Test
        @Throws(Exception::class)
        fun `check if create account in returns token when user is correct`() {

            val account = accountService.createAccount(AccountSignUpForm("test4", "1234", "emailTest4", false))

            val authorities = arrayListOf<String>()
            val token = tokenProvider.createToken(account.username, authorities)

            assertNotNull(token)
        }

        @Test
        fun `check if create account when user is correct`() {
            val userRegistrationForm = AccountSignUpForm("testUser6", "12", "email", false)
            val user = accountService.createAccount(userRegistrationForm)

            val userFromRepo = accountRepository.findById(user.id).get()
            assertEquals(user.id, userFromRepo.id)
        }

        @Test
        fun `check if encoder password when user created`() {
            val userRegistrationForm = AccountSignUpForm("testUser6", "12", "email", false)
            val user = accountService.createAccount(userRegistrationForm)

            assertNotSame(user.password, "12")
        }
    }

    @Nested
    inner class EmailAccount {

        @Test
        fun `check if create email verification token when user created`() {
            val account = AwesomeAccount("testUsername", "12", "email", "name", false)
            accountRepository.save(account)

            tokenService.createEmailVerificationToken(account, "qwerty")
            val emailToken = tokenRepository.findByTokenAndTokenType("qwerty", TokenType.REGISTRATION_EMAIL).get()

            assertNotNull(emailToken)
            assertEquals(emailToken.token, "qwerty")
        }


        @Test
        fun `check if get verification token when user created`() {
            val account = AwesomeAccount("testUsername", "12", "email", "name", false)
            accountRepository.save(account)

            val verificationToken = VerificationToken("q111", account, TokenType.REGISTRATION_EMAIL)
            tokenRepository.save(verificationToken)

            assertNotNull(tokenService.getEmailVerificationToken(verificationToken.token))
        }

        @Test
        fun `check if exists email when user created`() {
            val account = AwesomeAccount("testUsername", "12", "email", "name", false)
            accountRepository.save(account)

            assertEquals(true, accountService.existsByEmail(account.email))
        }

    }

    @Nested
    inner class UsernameAccount {

        @Test
        fun `check if exists by username when user created`() {
            val account = AwesomeAccount("testUsername", "12", "email", "name", false)
            accountRepository.save(account)

            assertEquals(true, accountService.existsByUsername(account.username))
        }

        @Test
        fun `check if find by username when user created`() {
            val account = AwesomeAccount("testUsername", "12", "email", "name", false)
            accountRepository.save(account)

            val accountFromService = accountService.findByUsername(account.username)

            assertEquals(accountFromService.username, account.username)
        }


        @Test
        fun `check if find by username throw when user created`() {
            val account = AwesomeAccount("testUsername", "12", "email", "name", false)
            accountRepository.save(account)

            org.junit.jupiter.api.assertThrows<UsernameNotFoundException> {
                accountService.findByUsername(account.username + "12")
            }
        }
    }

}

