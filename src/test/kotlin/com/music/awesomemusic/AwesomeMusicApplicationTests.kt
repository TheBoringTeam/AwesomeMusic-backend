package com.music.awesomemusic


import com.music.awesomemusic.persistence.domain.AwesomeAccount
import com.music.awesomemusic.persistence.dto.request.UserRegistrationForm
import com.music.awesomemusic.repositories.IAccountRepository
import com.music.awesomemusic.services.AccountService
import junit.framework.Assert.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@SpringBootTest
@RunWith(SpringRunner::class)
class AwesomeMusicApplicationTests {

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
    fun checkDatabaseUniqueValues() {
        val allAccounts = accountRepository.findAll()
        val account = AwesomeAccount("testUsername", "12125125",
                "test@mail.com", "some_name", false)

        accountService.saveAccount(account)

        val createdAccount = accountRepository.findById(1)
        assertEquals(0, allAccounts.count())

        assertNotNull(createdAccount)
    }
}
