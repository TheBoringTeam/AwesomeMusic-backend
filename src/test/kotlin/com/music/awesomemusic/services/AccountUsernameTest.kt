/* package com.music.awesomemusic.services

import com.music.awesomemusic.persistence.domain.AwesomeAccount
import com.music.awesomemusic.repositories.IAccountRepository
import com.music.awesomemusic.repositories.ITokenRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.context.junit4.SpringRunner


@SpringBootTest
@ExtendWith(SpringExtension::class)
class AccountUsernameTest {

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
    fun `check if exists by username when user created`() {
        val account = AwesomeAccount("testUsername","12", "email","name", false )
        accountRepository.save(account)

        assertEquals(true, accountService.existsByUsername(account.username))
    }

    @Test
    fun `check if find by username when user created`() {
        val account = AwesomeAccount("testUsername","12", "email","name", false )
        accountRepository.save(account)

        val accountFromService = accountService.findByUsername(account.username)

        assertEquals(accountFromService.username, account.username)
    }


    @Test
    fun `check if find by username throw when user created`() {
        val account = AwesomeAccount("testUsername","12", "email","name", false )
        accountRepository.save(account)

        assertThrows<UsernameNotFoundException> {
            accountService.findByUsername(account.username + "12")
        }
    }
} */


