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
class SignUpTest {
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
    fun shouldSignUpExpected201() {

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val request = HttpEntity<String>("{\"username\": \"test3\", \"password\": \"123HAha@%/+'!#$^?:.(){}[]~-_.\",\"email\":\"test3@mail.ru\",\"is_collective\": \"false\" }", headers)
        val responseEntity = restTemplate.postForEntity("http://localhost:$port/api/user/registration", request, String::class.java)

        assertEquals(responseEntity.statusCode, HttpStatus.CREATED)

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
    fun shouldSignUpIfLoginExistsExpected400() {

        val account = AccountSignUpForm("Violetta1234", "122334", "email", false)
        accountService.createAccount(account)

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON

        val request = HttpEntity<String>("{\"username\": \"Violetta1234\", \"password\": \"1233Agf%%%QWERTY%1qq\",\"email\":\"email@mail.ru\",\"is_collective\": \"false\" }", headers)
        val responseEntity = restTemplate.postForEntity("http://localhost:$port/api/user/registration", request, String::class.java)

        assertEquals(responseEntity.statusCode, HttpStatus.BAD_REQUEST)
    }

    @Test
    @Throws(Exception::class)
    fun shouldSignUpIfLoginExistsWithDifferentRegisterExpected201() {

        val account = AccountSignUpForm("Violetta1234", "122334", "email", false)
        accountService.createAccount(account)

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON

        val request = HttpEntity<String>("{\"username\": \"VIOLETTA1234\", \"password\": \"1233Agf%%%QWERTY%1qq\",\"email\":\"email@mail.ru\",\"is_collective\": \"false\" }", headers)
        val responseEntity = restTemplate.postForEntity("http://localhost:$port/api/user/registration", request, String::class.java)

        assertEquals(responseEntity.statusCode, HttpStatus.CREATED)
    }

    @Test
    @Throws(Exception::class)
    fun shouldSignUpIfLoginExistsWithDifferentRegister2Expected201() {

        val account = AccountSignUpForm("VIOLETTA1234", "122334", "email", false)
        accountService.createAccount(account)

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON

        val request = HttpEntity<String>("{\"username\": \"Violetta1234\", \"password\": \"1233Agf%%%QWERTY%1qq\",\"email\":\"email@mail.ru\",\"is_collective\": \"false\" }", headers)
        val responseEntity = restTemplate.postForEntity("http://localhost:$port/api/user/registration", request, String::class.java)

        assertEquals(responseEntity.statusCode, HttpStatus.CREATED)
    }


    @Test
    @Throws(Exception::class)
    fun shouldSignUpIfEmailExistsExpected400() {

        val accountSignUpForm = AccountSignUpForm("ViolettaVioletta", "122334", "email@email.ru", false)
        accountService.createAccount(accountSignUpForm)

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON

        val request = HttpEntity<String>("{\"username\": \"Violetta\", \"password\": \"1233Agf%%%QWERTY%1qq\",\"email\":\"email@email.ru\",\"is_collective\": \"false\" }", headers)
        val responseEntity = restTemplate.postForEntity("http://localhost:$port/api/user/registration", request, String::class.java)

        assertEquals(responseEntity.statusCode, HttpStatus.BAD_REQUEST)
    }
}