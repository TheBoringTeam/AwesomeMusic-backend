package com.music.awesomemusic.controllers

import com.music.awesomemusic.persistence.domain.AwesomeAccount
import com.music.awesomemusic.persistence.dto.request.AccountSignUpForm
import com.music.awesomemusic.repositories.IAccountRepository
import com.music.awesomemusic.repositories.ITokenRepository
import com.music.awesomemusic.security.tokens.JwtTokenProvider
import com.music.awesomemusic.services.AccountService
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
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

    @Before
    fun prepareAccount() {
        account = accountService.createAccount(AccountSignUpForm("holker", "123456", "test.mail@gmail.com", false))
    }

    @After
    fun deleteAccount() {
        tokenRepository.deleteAll()
        accountRepository.deleteAll()
    }

    @Test
    @Throws(Exception::class)
    fun signInShouldReturnOk() {
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val request = HttpEntity<String>("{ \"login\" : \"holker\", \"password\" : \"123456\" }", headers)
        val response = restTemplate.postForEntity("http://localhost:$port/api/user/sign-in", request,
                String::class.java)
        assertEquals(response.statusCode, HttpStatus.OK)
    }

    @Test
    fun meShouldReturnOk() {
        val token = tokenProvider.createToken(account.username, emptyList<String>())

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        headers.add("Authorization", "AwesomeToken $token")

        assertEquals(tokenProvider.validateToken(token), true)

        val request = HttpEntity<String>(headers)

        val response = restTemplate.exchange("http://localhost:$port/api/user/me",
                HttpMethod.GET, request, String::class.java)

        assertEquals(response.statusCode, HttpStatus.OK)
    }


    @Test
    fun changePasswordFunctionalityTest() {
        val token = tokenProvider.createToken(account.username, emptyList<String>())

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        headers.add("Authorization", "AwesomeToken $token")

        assertEquals(tokenProvider.validateToken(token), true)

        val request = HttpEntity<String>("{ \"old_password\" : \"123456\", \"new_password\" : \"12345\" }", headers)

        val response = restTemplate.exchange("http://localhost:$port/api/user/change-password",
                HttpMethod.PUT, request, String::class.java)

        accountService.isPasswordEquals("12345", account)

        assertEquals(response.statusCode, HttpStatus.OK)
    }
}