package com.music.awesomemusic.services

import com.music.awesomemusic.persistence.domain.AwesomeAccount
import com.music.awesomemusic.persistence.domain.EmailVerificationToken
import com.music.awesomemusic.repositories.IAccountRepository
import com.music.awesomemusic.repositories.IEmailTokenRepository
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner


@SpringBootTest
@RunWith(SpringRunner::class)
class AccountEmailTest {

    @Autowired
    lateinit var accountService: AccountService

    @Autowired
    lateinit var accountRepository: IAccountRepository

    @Autowired
    lateinit var emailTokenRepository: IEmailTokenRepository


    @Before
    fun init() {
        emailTokenRepository.deleteAll()
        accountRepository.deleteAll()

    }


    @Test
    fun contextLoads() {
    }


    @Test
    fun testCreateEmailVerificationTokenValid(){
        val account = AwesomeAccount("testUsername","12", "email","name", false )
        accountRepository.save(account)

        accountService.createEmailVerificationToken(account, "qwerty")
        val emailToken = emailTokenRepository.findByToken("qwerty")

        assertNotNull(emailToken)
        assertEquals(emailToken?.token,  "qwerty")
    }


    @Test
    fun testGetVerificationTokenValid() {
        val account = AwesomeAccount("testUsername","12", "email","name", false )
        accountRepository.save(account)

        val verificationToken = EmailVerificationToken("q111", account)
        emailTokenRepository.save(verificationToken)

        assertNotNull(accountService.getVerificationToken(verificationToken.token))
    }

    @Test
    fun testExistsByEmailValid() {
        val account = AwesomeAccount("testUsername","12", "email","name", false )
        accountRepository.save(account)

        assertEquals(true, accountService.existsByEmail(account.email))
    }
}


