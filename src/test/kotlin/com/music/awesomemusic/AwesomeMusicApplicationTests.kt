package com.music.awesomemusic

import com.music.awesomemusic.domain.persistence.AwesomeUser
import com.music.awesomemusic.domain.dto.UserRegistrationForm
import com.music.awesomemusic.repositories.IUserRepository
import com.music.awesomemusic.services.UserService
import junit.framework.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@SpringBootTest
@RunWith(SpringRunner::class)
class AwesomeMusicApplicationTests {

    @Autowired
    lateinit var userRepository: IUserRepository

    @Autowired
    lateinit var userService: UserService

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
        val allUsers = userRepository.findAll()
        val user = AwesomeUser("testUsername", "12125125",
                "test@mail.com")

        userRepository.save(user)
        val oneUser = userRepository.findAll()

        val createdUser = userRepository.findById(1)
        assertEquals(0, allUsers.count())

        assertNotNull(createdUser)
    }

    @Test
    fun checkUserCreateSuccess() {
        val userRegistrationForm = UserRegistrationForm("test_username", "12", "email")
        val user = userService.createUser(userRegistrationForm)

        assertNotSame(user.password, "12")
    }

}
