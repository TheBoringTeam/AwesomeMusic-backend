package com.music.awesomemusic.controllers

import com.music.awesomemusic.domain.dto.UserFormDto
import com.music.awesomemusic.models.AwesomeUser
import com.music.awesomemusic.services.UserService
import com.music.awesomemusic.utils.errors.MapValidationErrorService
import com.music.awesomemusic.utils.validators.UserValidator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*

@RestController("/api/user")
class UserController {

    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var mapValidator: MapValidationErrorService

    @Autowired
    lateinit var userValidator: UserValidator

    @GetMapping("/{id}")
    fun info(@PathVariable id: Long): ResponseEntity<*> {
        val user = userService.findById(id)
        return ResponseEntity<AwesomeUser>(user, HttpStatus.OK)
    }

    @GetMapping("/hello")
    fun hello(): ResponseEntity<*> {
        return ResponseEntity<String>("Heelo", HttpStatus.OK)
    }

    @PostMapping("/register")
    fun register(@RequestBody(required = true) userFormDto: UserFormDto, bindingResult: BindingResult): ResponseEntity<*> {
        userValidator.validate(userFormDto, bindingResult)
        val errorMap = mapValidator.createErrorMap(bindingResult)

        if (errorMap != null) {
            return errorMap
        }

        userService.createUser(userFormDto)
        return ResponseEntity<String>(HttpStatus.CREATED)
    }
}