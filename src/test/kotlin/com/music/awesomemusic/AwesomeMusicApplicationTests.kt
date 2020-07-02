/* package com.music.awesomemusic

import com.music.awesomemusic.persistence.domain.AwesomeAccount
import com.music.awesomemusic.repositories.IAccountRepository
import com.music.awesomemusic.repositories.ITokenRepository
import com.music.awesomemusic.services.AccountService
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
class AwesomeMusicApplicationTests {

    @Autowired
    lateinit var accountService: AccountService

    @Autowired
    lateinit var accountRepository: IAccountRepository

    @Autowired
    lateinit var tokenRepository: ITokenRepository


    @BeforeEach
    fun init() {
        tokenRepository.deleteAll()
        accountRepository.deleteAll()

    }


    @Test
    fun contextLoads() {
    }


    @Test
    fun `check if database unigue values when create user`() {
        val allAccounts = accountRepository.findAll()
        val account = AwesomeAccount("testUsername", "12125125",
                "test@mail.com", "some_name", false)

        accountService.saveAccount(account)

        val createdAccount = accountRepository.findById(1)
        assertEquals(0, allAccounts.count())

        assertNotNull(createdAccount)
    }
}


*/