package com.music.awesomemusic

import com.music.awesomemusic.models.AwesomeUser
import com.music.awesomemusic.repositories.IUserRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull

@SpringBootTest
class AwesomeMusicApplicationTests {

    @Autowired
    lateinit var userRepository: IUserRepository

    @Test
    fun contextLoads() {
    }

    @Test
    @DisplayName("Test that sums two numbers")
    fun sumTwoNumber() {
        val result = 6
        assertEquals(result,6)
    }


    @Test
    @DisplayName("Test database unique")
    fun checkUnique() {
        val allUsers = userRepository.findAll()
        val user = AwesomeUser("testUsername", "12125125",
                "test@mail.com")

        userRepository.save(user)
        val oneUser = userRepository.findAll()

        val createdUser = userRepository.findById(1)
        assertEquals(0, allUsers.count())
        assertEquals(1, oneUser.count())

        assertNotNull(createdUser)
    }
}
