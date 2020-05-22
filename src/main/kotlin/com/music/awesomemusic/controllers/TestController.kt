package com.music.awesomemusic.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

/**
 * Test controller. Put any test endings (not unit test!).
 */
@RestController
class TestController {
    @GetMapping("/")
    @ResponseBody
    fun home(): String? {
        return "Welcome home!"
    }

    @RequestMapping("/restricted")
    @ResponseBody
    fun restricted(): String? {
        return "You found the secret lair!"
    }
}