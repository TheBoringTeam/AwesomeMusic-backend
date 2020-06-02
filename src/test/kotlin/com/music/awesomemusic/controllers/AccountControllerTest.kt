package com.music.awesomemusic.controllers

import com.music.awesomemusic.repositories.IAccountRepository
import com.music.awesomemusic.security.tokens.JwtTokenProvider
import com.music.awesomemusic.services.AccountService
import com.music.awesomemusic.services.AccountUserDetailsService
import com.music.awesomemusic.services.TokenService
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(AccountController::class)
@RunWith(SpringRunner::class)
class AccountControllerTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var accountRepository: IAccountRepository

    @MockBean
    lateinit var accountService: AccountService

    @MockBean
    lateinit var userDetailsService: AccountUserDetailsService

    @MockBean
    lateinit var jwtTokenProvider: JwtTokenProvider

    @MockBean
    lateinit var tokenService: TokenService

    @Test
    @Throws(Exception::class)
    fun helloShouldReturn403() {
        mockMvc.perform(post("/api/user/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"login\": \"holker\", \"password\": \"12412412512\" }")
        ).andDo(print()).andExpect(status().`is`(403))
    }
}