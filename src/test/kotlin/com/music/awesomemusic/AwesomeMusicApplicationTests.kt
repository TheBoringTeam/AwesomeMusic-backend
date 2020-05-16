package com.music.awesomemusic

import com.music.awesomemusic.persistence.domain.AwesomeAccount
import com.music.awesomemusic.persistence.domain.EmailVerificationToken
import com.music.awesomemusic.persistence.dto.request.UserRegistrationForm
import com.music.awesomemusic.repositories.IAccountRepository
import com.music.awesomemusic.repositories.IEmailTokenRepository
import com.music.awesomemusic.services.AccountService
import junit.framework.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.test.context.junit4.SpringRunner


@SpringBootTest
@RunWith(SpringRunner::class)
class AwesomeMusicApplicationTests {

    @Autowired
    lateinit var accountService: AccountService

    @Autowired
    lateinit var accountRepository: IAccountRepository

    @Autowired
    lateinit var emailTokenRepository: IEmailTokenRepository


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

    @Test
    fun checkExistsByUsername() {
        val userRegistrationForm = UserRegistrationForm("testUser4", "12", "email", false)
        val user = accountService.createAccount(userRegistrationForm)

        assertEquals(accountRepository.existsByUsername(user.username), accountService.existsByUsername(user.username))
    }

    @Test
    fun checkCreateEmailVerificationToken(){
        val userRegistrationForm = UserRegistrationForm("testUser5", "12", "email", false)
        val user = accountService.createAccount(userRegistrationForm)

        val verificationToken = EmailVerificationToken("abcd", user)
        emailTokenRepository.save(verificationToken)

        assertEquals(verificationToken.token, "abcd")
    }

    @Test
    fun checkSaveUser() {
        val userRegistrationForm = UserRegistrationForm("testUser6", "12", "email", false)
        val user = accountService.createAccount(userRegistrationForm)
        accountService.saveAccount(user)
        val userFromRepo = accountRepository.findById(user.id).get()
        assertEquals(user.id,userFromRepo.id)
    }

    @Test
    fun checkGetVerificationToken() {
        val userRegistrationForm = UserRegistrationForm("testUser7", "12", "email", false)
        val user = accountService.createAccount(userRegistrationForm)

        val verificationToken = EmailVerificationToken("q111", user)
        emailTokenRepository.save(verificationToken)

        assertNotNull(accountService.getVerificationToken(verificationToken.token))
    }

    @Test
    fun checkExistsByEmail() {
        val userRegistrationForm = UserRegistrationForm("testUser8", "pass", "testTest2@gmail.com", false)
        val user = accountService.createAccount(userRegistrationForm)

        assertEquals(accountRepository.existsByEmail("testTest2@gmail.com"), accountService.existsByEmail(user.email))
    }


    @Test
    fun checkFindByUsername() {
        val userRegistrationForm = UserRegistrationForm("testUser3", "123", "eee", false)
        val user = accountService.createAccount(userRegistrationForm)

        assertThrows(UsernameNotFoundException::class.java) {
            accountRepository.findByUsername("").orElseThrow() {
            UsernameNotFoundException("Username was not found")
            }
        }
    }



}


