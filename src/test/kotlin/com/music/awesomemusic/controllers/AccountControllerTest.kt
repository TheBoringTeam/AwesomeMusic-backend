package com.music.awesomemusic.controllers

import com.music.awesomemusic.persistence.domain.AwesomeAccount
import com.music.awesomemusic.persistence.dto.request.AccountSignUpForm
import com.music.awesomemusic.repositories.IAccountRepository
import com.music.awesomemusic.repositories.ITokenRepository
import com.music.awesomemusic.security.tokens.JwtTokenProvider
import com.music.awesomemusic.services.AccountService
import com.music.awesomemusic.utils.exceptions.basic.WrongArgumentsException
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.exchange
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.*
import org.springframework.test.context.junit4.SpringRunner



@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AccountControllerTest {
    @LocalServerPort
    private var port: Int = 0

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Autowired
    private lateinit var accountService: AccountService

    @Autowired
    private lateinit var accountRepository: IAccountRepository

    @Autowired
    private lateinit var tokenProvider: JwtTokenProvider

    @Autowired
    private lateinit var tokenRepository: ITokenRepository

    @After
    fun deleteAccount() {
        tokenRepository.deleteAll()
        accountRepository.deleteAll()
    }


    @Test
    @Throws(Exception::class)
    fun shouldMeIfAccountExistsExpected200() {

        val account = accountService.createAccount(AccountSignUpForm("test5", "1234", "emailTest5", false))
        val authorities = arrayListOf<String>()

        val token = tokenProvider.createToken(account.username, authorities)

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON

        headers.set("Authorization", "AwesomeToken $token")


        val request = HttpEntity<String>(headers)
        val responseEntity = restTemplate.exchange("http://localhost:$port/api/user/me", HttpMethod.GET, request, String::class.java)

        assertEquals(responseEntity.statusCode, HttpStatus.OK)

    }

    @Test
    @Throws(Exception::class)
    fun shouldMeIfAccountNoExistsExpected403() {

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON


        val request = HttpEntity<String>(headers)
        val responseEntity = restTemplate.exchange("http://localhost:$port/api/user/me", HttpMethod.GET, request, String::class.java)

        assertEquals(responseEntity.statusCode, HttpStatus.FORBIDDEN)

    }

    @Test
    fun shouldPasswordResetExpected200() {
        val account = accountService.createAccount(AccountSignUpForm("test6", "1234", "emailTest6@mail.ru", false))

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val request = HttpEntity<String>("{\"email\":\"emailTest6@mail.ru\"}", headers)
        val responseEntity = restTemplate.postForEntity("http://localhost:$port/api/user/reset-password", request, String::class.java)

        assertEquals(responseEntity.statusCode, HttpStatus.OK)
    }

    @Test
    fun shouldChangePasswordExpected200() {
        val account = accountService.createAccount(AccountSignUpForm("test7", "1234rgsrsQEW%%", "test7@mail.ru", false))
        val authorities = arrayListOf<String>()

        val token = tokenProvider.createToken(account.username, authorities)

        assertEquals(tokenProvider.validateToken(token), true)
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        headers.set("Authorization", "AwesomeToken $token")


        val request = HttpEntity<String>("{\"old_password\":\"1234rgsrsQEW%%\",\"new_password\":\"QwertY1234&&\" }", headers)
        val responseEntity = restTemplate.exchange("http://localhost:$port/api/user/change-password", HttpMethod.PUT, request, String::class.java)

        assertEquals(responseEntity.statusCode, HttpStatus.OK)
    }


}

