package com.music.awesomemusic.controllers

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {
    @PostMapping("/")
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