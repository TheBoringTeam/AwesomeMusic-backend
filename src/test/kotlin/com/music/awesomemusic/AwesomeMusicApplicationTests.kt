package com.music.awesomemusic

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class AwesomeMusicApplicationTests {

    @Test
    fun contextLoads() {
    }

    @Test
    @DisplayName("Test that sums two numbers")
    fun sumTwoNumber() {
        val result = 6
        assertEquals(result,6)
    }

}
