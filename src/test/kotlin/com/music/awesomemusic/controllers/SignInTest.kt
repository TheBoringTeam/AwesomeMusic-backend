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
class SignInTest {
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
    fun shouldSignInWithUsernameExpected200() {

        accountService.createAccount(AccountSignUpForm("test1", "1234", "emailTest1", false))
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val request = HttpEntity<String>("{\"login\": \"test1\", \"password\": \"1234\"}", headers)
        val responseEntity = restTemplate.postForEntity("http://localhost:$port/api/user/sign-in", request, String::class.java)

        assertEquals(responseEntity.statusCode, HttpStatus.OK)
    }

    @Test
    @Throws(Exception::class)
    fun shouldSignInWithEmailExpected200() {

        accountService.createAccount(AccountSignUpForm("test2", "1234", "emailTest2", false))
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val request = HttpEntity<String>("{\"login\": \"emailTest2\", \"password\": \"1234\"}", headers)
        val responseEntity = restTemplate.postForEntity("http://localhost:$port/api/user/sign-in", request, String::class.java)

        assertEquals(responseEntity.statusCode, HttpStatus.OK)
    }

    @Test
    @Throws(Exception::class)
    fun shouldSignInWithEmailIfNoExistsExpected403() {

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val request = HttpEntity<String>("{\"login\": \"emailTest3@gmail.com\", \"password\": \"1234\"}", headers)
        val responseEntity = restTemplate.postForEntity("http://localhost:$port/api/user/sign-in", request, String::class.java)

        assertEquals(responseEntity.statusCode, HttpStatus.FORBIDDEN)
    }

    @Test
    @Throws(Exception::class)
    fun shouldSignInWithUsernameIfNoExistsExpected403() {

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val request = HttpEntity<String>("{\"login\": \"testName\", \"password\": \"1234\"}", headers)
        val responseEntity = restTemplate.postForEntity("http://localhost:$port/api/user/sign-in", request, String::class.java)

        assertEquals(responseEntity.statusCode, HttpStatus.FORBIDDEN)
    }

    @Test
    @Throws(Exception::class)
    fun shouldSignInReturnTokenExpected200() {

        val account = accountService.createAccount(AccountSignUpForm("testName", "1234", "emailTest10", false))
        val authorities = arrayListOf<String>()

        val token = tokenProvider.createToken(account.username, authorities)

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON

        val request = HttpEntity<String>("{\"login\": \"testName\", \"password\": \"1234\"}", headers)
        val responseEntity = restTemplate.postForEntity("http://localhost:$port/api/user/sign-in", request, String::class.java)

        assertEquals(tokenProvider.validateToken(token), true)

        assertEquals(responseEntity.statusCode, HttpStatus.OK)

    }

}

