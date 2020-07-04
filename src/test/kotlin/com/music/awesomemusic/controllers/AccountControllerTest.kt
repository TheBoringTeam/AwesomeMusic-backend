package com.music.awesomemusic.controllers

import com.music.awesomemusic.persistence.domain.AwesomeAccount
import com.music.awesomemusic.persistence.dto.request.AccountSignUpForm
import com.music.awesomemusic.repositories.IAccountRepository
import com.music.awesomemusic.repositories.ITokenRepository
import com.music.awesomemusic.security.tokens.JwtTokenProvider
import com.music.awesomemusic.services.AccountService
import com.music.awesomemusic.utils.exceptions.basic.WrongArgumentsException

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
//import org.junit.After
//import org.junit.Before

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
//import org.junit.runner.RunWith

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.exchange
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.*
//import org.springframework.test.context.junit4.SpringRunner

import org.springframework.boot.context.properties.NestedConfigurationProperty
import org.springframework.test.context.junit.jupiter.SpringExtension


@ExtendWith(SpringExtension::class)
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

    @AfterEach
    fun deleteAccount() {
        tokenRepository.deleteAll()
        accountRepository.deleteAll()
    }


    @Nested
    inner class SignIn {

        @Test

        fun `check if sign in returns 200 when login with username`() {

            accountService.createAccount(AccountSignUpForm("test1", "1234", "emailTest1", false))
            val headers = HttpHeaders()
            headers.contentType = MediaType.APPLICATION_JSON
            val request = HttpEntity<String>("{\"login\": \"test1\", \"password\": \"1234\"}", headers)
            val responseEntity = restTemplate.postForEntity("http://localhost:$port/api/user/sign-in", request, String::class.java)

            assertEquals(responseEntity.statusCode, HttpStatus.OK)
        }

        @Test
        @Throws(Exception::class)
        fun `check if sign in returns 403 when password is wrong`() {

            accountService.createAccount(AccountSignUpForm("test3", "1234", "emailTest1", false))
            val headers = HttpHeaders()
            headers.contentType = MediaType.APPLICATION_JSON
            val request = HttpEntity<String>("{\"login\": \"test3\", \"password\": \"12345\"}", headers)
            val responseEntity = restTemplate.postForEntity("http://localhost:$port/api/user/sign-in", request, String::class.java)

            assertEquals(responseEntity.statusCode, HttpStatus.FORBIDDEN)
        }

        @Test
        @Throws(Exception::class)
        fun `check if sign in returns 403 when email no exists`() {

            val headers = HttpHeaders()
            headers.contentType = MediaType.APPLICATION_JSON
            val request = HttpEntity<String>("{\"login\": \"emailTest3@gmail.com\", \"password\": \"1234\"}", headers)
            val responseEntity = restTemplate.postForEntity("http://localhost:$port/api/user/sign-in", request, String::class.java)

            assertEquals(responseEntity.statusCode, HttpStatus.FORBIDDEN)
        }

        @Test
        @Throws(Exception::class)
        fun `check if sign in returns 403 when user no exists`() {

            val headers = HttpHeaders()
            headers.contentType = MediaType.APPLICATION_JSON
            val request = HttpEntity<String>("{\"login\": \"testName\", \"password\": \"1234\"}", headers)
            val responseEntity = restTemplate.postForEntity("http://localhost:$port/api/user/sign-in", request, String::class.java)

            assertEquals(responseEntity.statusCode, HttpStatus.FORBIDDEN)
        }

        @Test
        @Throws(Exception::class)
        fun `check if sign in returns 200 and token when user is correct`() {

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

        @Test
        @Throws(Exception::class)
        fun `check if sign in returns 200 when login with email`() {

            accountService.createAccount(AccountSignUpForm("test2", "1234", "emailTest2", false))
            val headers = HttpHeaders()
            headers.contentType = MediaType.APPLICATION_JSON
            val request = HttpEntity<String>("{\"login\": \"emailTest2\", \"password\": \"1234\"}", headers)
            val responseEntity = restTemplate.postForEntity("http://localhost:$port/api/user/sign-in", request, String::class.java)

            assertEquals(responseEntity.statusCode, HttpStatus.OK)
        }

    }


    @Nested
    inner class SignUp {

        @Test
        @Throws(Exception::class)
        fun `check if sign up returns 201 when user is correct`() {

            val headers = HttpHeaders()
            headers.contentType = MediaType.APPLICATION_JSON
            val request = HttpEntity<String>("{\"username\": \"test3\", \"password\": \"123HAha@%/+'!#$^?:.(){}[]~-_.\",\"email\":\"test3@mail.ru\",\"is_collective\": \"false\" }", headers)
            val responseEntity = restTemplate.postForEntity("http://localhost:$port/api/user/registration", request, String::class.java)

            assertEquals(responseEntity.statusCode, HttpStatus.CREATED)

        }
        @Test
        @Throws(Exception::class)
        fun `check if sign up returns 400 when password less 6`() {

            val headers = HttpHeaders()
            headers.contentType = MediaType.APPLICATION_JSON

            val request = HttpEntity<String>("{\"username\": \"viovio\", \"password\": \"12345\",\"email\":\"test3@mail.ru\",\"is_collective\": \"false\" }", headers)
            val responseEntity = restTemplate.postForEntity("http://localhost:$port/api/user/registration", request, String::class.java)

            assertEquals(responseEntity.statusCode, HttpStatus.BAD_REQUEST)
        }


        @Test
        @Throws(Exception::class)
        fun `check if sign up returns 400 when password more 32`() {

            val headers = HttpHeaders()
            headers.contentType = MediaType.APPLICATION_JSON

            val request = HttpEntity<String>("{\"username\": \"viovio\", \"password\": \"1234567890HaHaHaHAHAHAHAHAHAHAH%%%%%%%%%%%%%%%%\",\"email\":\"test3@mail.ru\",\"is_collective\": \"false\" }", headers)
            val responseEntity = restTemplate.postForEntity("http://localhost:$port/api/user/registration", request, String::class.java)

            assertEquals(responseEntity.statusCode, HttpStatus.BAD_REQUEST)
        }


        @Test
        @Throws(Exception::class)
        fun `check if sign up returns 400 when email less 5`() {

            val headers = HttpHeaders()
            headers.contentType = MediaType.APPLICATION_JSON

            val request = HttpEntity<String>("{\"username\": \"Violetta\", \"password\": \"1233Agf%%%QWERTY%1qq\",\"email\":\"1@.r\",\"is_collective\": \"false\" }", headers)
            val responseEntity = restTemplate.postForEntity("http://localhost:$port/api/user/registration", request, String::class.java)

            assertEquals(responseEntity.statusCode, HttpStatus.BAD_REQUEST)
        }




        @Test
        @Throws(Exception::class)
        fun `check if sign up returns 400 when password more 254`() {

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
        fun `check if sign up returns 400 when username less 5`() {

            val headers = HttpHeaders()
            headers.contentType = MediaType.APPLICATION_JSON

            val request = HttpEntity<String>("{\"username\": \"Viol\", \"password\": \"1233Agf%%%QWERTY%1qq\",\"email\":\"1@.r\",\"is_collective\": \"false\" }", headers)
            val responseEntity = restTemplate.postForEntity("http://localhost:$port/api/user/registration", request, String::class.java)

            assertEquals(responseEntity.statusCode, HttpStatus.BAD_REQUEST)
        }

        @Test
        @Throws(Exception::class)
        fun `check if sign up returns 400 when username more 16`() {

            val headers = HttpHeaders()
            headers.contentType = MediaType.APPLICATION_JSON

            val request = HttpEntity<String>("{\"username\": \"ViolettaVioletta1\", \"password\": \"1233Agf%%%QWERTY%1qq\",\"email\":\"1@.r\",\"is_collective\": \"false\" }", headers)
            val responseEntity = restTemplate.postForEntity("http://localhost:$port/api/user/registration", request, String::class.java)

            assertEquals(responseEntity.statusCode, HttpStatus.BAD_REQUEST)
        }

        @Test
        @Throws(Exception::class)
        fun `check if sign up returns 400 when username exists`() {

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
        fun `check if sign up returns 201 when username exists with different register`() {

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
        fun `check if sign up returns 201 when username exists with different register2`() {

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
        fun `check if sign up returns 400 when email exists`() {

            val accountSignUpForm = AccountSignUpForm("ViolettaVioletta", "122334", "email@email.ru", false)
            accountService.createAccount(accountSignUpForm)

            val headers = HttpHeaders()
            headers.contentType = MediaType.APPLICATION_JSON

            val request = HttpEntity<String>("{\"username\": \"Violetta\", \"password\": \"1233Agf%%%QWERTY%1qq\",\"email\":\"email@email.ru\",\"is_collective\": \"false\" }", headers)
            val responseEntity = restTemplate.postForEntity("http://localhost:$port/api/user/registration", request, String::class.java)

            assertEquals(responseEntity.statusCode, HttpStatus.BAD_REQUEST)
        }
    }

    @Nested
    inner class Other {
        @Test
        @Throws(Exception::class)
        fun `check if me returns 200 when user exists`() {

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
        fun `check if me returns 403 when user no exists`() {

            val headers = HttpHeaders()
            headers.contentType = MediaType.APPLICATION_JSON


            val request = HttpEntity<String>(headers)
            val responseEntity = restTemplate.exchange("http://localhost:$port/api/user/me", HttpMethod.GET, request, String::class.java)

            assertEquals(responseEntity.statusCode, HttpStatus.FORBIDDEN)

        }

        @Test
        fun `check if password reset returns 200 when user created`() {
            val account = accountService.createAccount(AccountSignUpForm("test6", "1234", "emailTest6@mail.ru", false))

            val headers = HttpHeaders()
            headers.contentType = MediaType.APPLICATION_JSON
            val request = HttpEntity<String>("{\"email\":\"emailTest6@mail.ru\"}", headers)
            val responseEntity = restTemplate.postForEntity("http://localhost:$port/api/user/reset-password", request, String::class.java)

            assertEquals(responseEntity.statusCode, HttpStatus.OK)
        }

        @Test
        fun `check if change password returns 200 when user created`() {
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
}

