package com.music.awesomemusic.services

import com.music.awesomemusic.persistence.domain.AwesomeAccount
import com.music.awesomemusic.persistence.domain.EmailVerificationToken
import com.music.awesomemusic.persistence.dto.request.UserRegistrationForm
import com.music.awesomemusic.repositories.IAccountRepository
import junit.framework.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.assertThrows
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.test.context.junit4.SpringRunner


@SpringBootTest
@RunWith(SpringRunner::class)
class AccountUsernameTest {

    @Autowired
    lateinit var accountService: AccountService

    @Autowired
    lateinit var accountRepository: IAccountRepository


    @Before
    fun init() {
        accountRepository.deleteAll()

    }


    @Test
    fun contextLoads() {
    }


    @Test
    fun testExistsByUsernameValid() {
        val account = AwesomeAccount("testUsername","12", "email","name", false )
        accountRepository.save(account)

        assertEquals(true, accountService.existsByUsername(account.username))
    }

    @Test
    fun testFindByUsername() {
        val account = AwesomeAccount("testUsername","12", "email","name", false )
        accountRepository.save(account)

        val accountFromService = accountService.findByUsername(account.username)

        assertEquals(accountFromService.username, account.username)
    }


    @Test
    fun testFindByUsernameWithThrowExceptionFail() {
        val account = AwesomeAccount("testUsername","12", "email","name", false )
        accountRepository.save(account)

        assertThrows<UsernameNotFoundException> {
            accountService.findByUsername(account.username + "12")
        }
    }
}


