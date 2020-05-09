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

    @After
    fun finish() {
        accountRepository.deleteAll()
    }

    @Test
    fun contextLoads() {
    }

    @Test
    fun sumTwoNumber() {
        val result = 6
        assertEquals(result, 6)
    }


    @Test
    fun checkUnique() {
        val allAccounts = accountRepository.findAll()
        val account = AwesomeAccount("testUsername", "12125125",
                "test@mail.com", "some_name", false)

        accountService.saveAccount(account)

        val createdAccount = accountRepository.findById(1)
        assertEquals(0, allAccounts.count())

        assertNotNull(createdAccount)
    }

    @Test
    fun checkUserCreateSuccess() {
        val userRegistrationForm = UserRegistrationForm("test_username", "12", "email", false)
        val user = accountService.createAccount(userRegistrationForm)

        assertNotSame(user.password, "12")
    }

}
