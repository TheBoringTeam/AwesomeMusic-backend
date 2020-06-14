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

    lateinit var account: AwesomeAccount

    // @Before
    //  fun prepareAccount() {
    //     account = accountService.createAccount(AccountSignUpForm("holker", "123456", "test.mail@gmail.com", false))
    //  }

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
    fun shouldSignUpExpected201() {

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val request = HttpEntity<String>("{\"username\": \"test3\", \"password\": \"123HAha@%/+'!#$^?:.(){}[]~-_.\",\"email\":\"test3@mail.ru\",\"is_collective\": \"false\" }", headers)
        val responseEntity = restTemplate.postForEntity("http://localhost:$port/api/user/registration", request, String::class.java)

        assertEquals(responseEntity.statusCode, HttpStatus.CREATED)

    }


    @Test
    @Throws(Exception::class)
    fun shouldSignUpToReturnToken() {

        val account = accountService.createAccount(AccountSignUpForm("test4", "1234", "emailTest4", false))

        val authorities = arrayListOf<String>()
        val token = tokenProvider.createToken(account.username, authorities)

        assertNotNull(token)
    }

    @Test
    @Throws(Exception::class)
    fun shouldSignUpIfPasswordLess6Expected400() {

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON

        val request = HttpEntity<String>("{\"username\": \"viovio\", \"password\": \"12345\",\"email\":\"test3@mail.ru\",\"is_collective\": \"false\" }", headers)
        val responseEntity = restTemplate.postForEntity("http://localhost:$port/api/user/registration", request, String::class.java)

        assertEquals(responseEntity.statusCode, HttpStatus.BAD_REQUEST)
    }

    @Test
    @Throws(Exception::class)
    fun shouldSignUpIfPasswordMore32Expected400() {

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON

        val request = HttpEntity<String>("{\"username\": \"viovio\", \"password\": \"1234567890HaHaHaHAHAHAHAHAHAHAH%%%%%%%%%%%%%%%%\",\"email\":\"test3@mail.ru\",\"is_collective\": \"false\" }", headers)
        val responseEntity = restTemplate.postForEntity("http://localhost:$port/api/user/registration", request, String::class.java)

        assertEquals(responseEntity.statusCode, HttpStatus.BAD_REQUEST)
    }


    @Test
    @Throws(Exception::class)
    fun shouldSignUpIfEmailLess5Expected400() {

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON

        val request = HttpEntity<String>("{\"username\": \"Violetta\", \"password\": \"1233Agf%%%QWERTY%1qq\",\"email\":\"1@.r\",\"is_collective\": \"false\" }", headers)
        val responseEntity = restTemplate.postForEntity("http://localhost:$port/api/user/registration", request, String::class.java)

        assertEquals(responseEntity.statusCode, HttpStatus.BAD_REQUEST)
    }

    @Test
    @Throws(Exception::class)
    fun shouldSignUpIfEmailMore254Expected400() {

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON

        val request = HttpEntity<String>("{\"username\": \"Violetta\", \"password\": \"1233Agf%%%QWERTY%1qq" +
                "qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq" +
                "qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq\",\"email\":\"1@.r\",\"is_collective\": \"false\" }", headers)
        val responseEntity = restTemplate.postForEntity("http://localhost:$port/api/user/registration", request, String::class.java)

        assertEquals(responseEntity.statusCode, HttpStatus.BAD_REQUEST)
    }

    @Test
    @Throws(Exception::class)
    fun shouldSignUpIfLoginLess5Expected400() {

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON

        val request = HttpEntity<String>("{\"username\": \"Viol\", \"password\": \"1233Agf%%%QWERTY%1qq\",\"email\":\"1@.r\",\"is_collective\": \"false\" }", headers)
        val responseEntity = restTemplate.postForEntity("http://localhost:$port/api/user/registration", request, String::class.java)

        assertEquals(responseEntity.statusCode, HttpStatus.BAD_REQUEST)
    }

    @Test
    @Throws(Exception::class)
    fun shouldSignUpIfLoginMore16Expected400() {

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON

        val request = HttpEntity<String>("{\"username\": \"ViolettaVioletta1\", \"password\": \"1233Agf%%%QWERTY%1qq\",\"email\":\"1@.r\",\"is_collective\": \"false\" }", headers)
        val responseEntity = restTemplate.postForEntity("http://localhost:$port/api/user/registration", request, String::class.java)

        assertEquals(responseEntity.statusCode, HttpStatus.BAD_REQUEST)
    }


    @Test
    @Throws(Exception::class)
    fun shouldMeIfAccountExistsExpected200() {

        val account = accountService.createAccount(AccountSignUpForm("test5", "1234", "emailTest5", false))
        val authorities = arrayListOf<String>()

        val jwtTokenProvider = tokenProvider.createToken(account.username, authorities)

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON

        headers.set("Authorization", "AwesomeToken $jwtTokenProvider")


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

        val jwtTokenProvider = tokenProvider.createToken(account.username, authorities)

        assertEquals(tokenProvider.validateToken(jwtTokenProvider), true)
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        headers.set("Authorization", "AwesomeToken $jwtTokenProvider")


        val request = HttpEntity<String>("{\"old_password\":\"1234rgsrsQEW%%\",\"new_password\":\"QwertY1234&&\" }", headers)
        val responseEntity = restTemplate.exchange("http://localhost:$port/api/user/change-password", HttpMethod.PUT, request, String::class.java)

        assertEquals(responseEntity.statusCode, HttpStatus.OK)
    }


}

