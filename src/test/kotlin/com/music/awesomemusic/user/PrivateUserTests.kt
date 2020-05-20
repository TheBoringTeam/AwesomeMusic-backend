package com.music.awesomemusic.user

import com.music.awesomemusic.persistence.dto.request.AccountSignUpForm
import com.music.awesomemusic.repositories.IAccountRepository
import com.music.awesomemusic.repositories.IEmailTokenRepository
import com.music.awesomemusic.services.AccountService
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertNotSame
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@SpringBootTest
@RunWith(SpringRunner::class)
class PrivateUserTests {
    @Autowired
    lateinit var accountService: AccountService

    @Autowired
    lateinit var accountRepository: IAccountRepository

    @Autowired
    lateinit var emailTokenRepository: IEmailTokenRepository

    @Before
    fun init() {
        accountRepository.deleteAll()
        emailTokenRepository.deleteAll()
    }

}
