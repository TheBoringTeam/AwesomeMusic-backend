package com.music.awesomemusic

import com.music.awesomemusic.domain.AwesomeUser
import com.music.awesomemusic.repositories.IUserRepository
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
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

    @Test
    fun contextLoads() {
    }

    @Test
    fun sumTwoNumber() {
        val result = 6
        assertEquals(result,6)
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
}
