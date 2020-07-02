/*
package com.music.awesomemusic.services

import com.music.awesomemusic.persistence.domain.AwesomeAccount
import com.music.awesomemusic.persistence.domain.TokenType
import com.music.awesomemusic.persistence.domain.VerificationToken
import com.music.awesomemusic.repositories.IAccountRepository
import com.music.awesomemusic.repositories.ITokenRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals


import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.context.junit4.SpringRunner


@SpringBootTest
@ExtendWith(SpringExtension::class)
class AccountEmailTest {

    @Autowired
    lateinit var accountService: AccountService

    @Autowired
    lateinit var accountRepository: IAccountRepository

    @Autowired
    lateinit var tokenService: TokenService

    @Autowired
    lateinit var tokenRepository: ITokenRepository


    @BeforeEach
    fun init() {
        tokenRepository.deleteAll()
        accountRepository.deleteAll()

    }

    @AfterEach
    fun after() {
        tokenRepository.deleteAll()
        accountRepository.deleteAll()

    }


    @Test
    fun contextLoads() {
    }


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


*/
