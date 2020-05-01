package com.music.awesomemusic.controllers

import org.springframework.web.bind.annotation.*

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