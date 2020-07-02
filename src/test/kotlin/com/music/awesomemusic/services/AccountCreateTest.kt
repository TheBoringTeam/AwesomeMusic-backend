/*package com.music.awesomemusic.services

import com.music.awesomemusic.persistence.domain.AwesomeAccount
import com.music.awesomemusic.persistence.dto.request.AccountSignUpForm
import com.music.awesomemusic.repositories.IAccountRepository
import com.music.awesomemusic.repositories.ITokenRepository
import com.music.awesomemusic.security.tokens.JwtTokenProvider
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension



@SpringBootTest
@ExtendWith(SpringExtension::class)
class AccountCreateTest {

    @Autowired
    lateinit var accountService: AccountService

    @Autowired
    lateinit var accountRepository: IAccountRepository

    @Autowired
    lateinit var tokenRepository: ITokenRepository

    @Autowired
    private lateinit var tokenProvider: JwtTokenProvider


    @BeforeEach
    fun init() {
        tokenRepository.deleteAll()
        accountRepository.deleteAll()

    }


    @Test
    fun contextLoads() {
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


*/