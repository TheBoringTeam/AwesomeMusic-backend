package com.music.awesomemusic.controllers

import com.music.awesomemusic.models.AuthRequest
import com.music.awesomemusic.models.AwesomeUser
import com.music.awesomemusic.repositories.IUserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.lang.Exception

@RestController
class UserController {

    @Autowired
    lateinit var userRepository: IUserRepository

    @GetMapping("/hello")
    fun hello(): ResponseEntity<*> {
        val users = userRepository.getAll()
        return ResponseEntity<List<AwesomeUser>>(users, HttpStatus.OK)
    }

}