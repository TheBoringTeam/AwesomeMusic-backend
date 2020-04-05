package com.music.awesomemusic.controllers

import com.music.awesomemusic.domain.dto.UserFormDto
import com.music.awesomemusic.models.AwesomeUser
import com.music.awesomemusic.services.UserService
import com.music.awesomemusic.utils.errors.MapValidationErrorService
import com.music.awesomemusic.utils.validators.UserValidator
import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("api/user")
class UserController {

    private val logger = Logger.getLogger(UserController::class.java)

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
        logger.debug("Hello ending")
        return ResponseEntity<String>("Heelo", HttpStatus.OK)
    }

    @PostMapping("/register")
    fun register(@RequestBody(required = true) userFormDto: UserFormDto, bindingResult: BindingResult): ResponseEntity<*> {
        logger.debug("Start register process")
        userValidator.validate(userFormDto, bindingResult)
        val errorMap = mapValidator.createErrorMap(bindingResult)

        if (errorMap != null) {
            logger.error("Registration validation failed")
            return errorMap
        }

        userService.createUser(userFormDto)
        logger.debug("User was created")
        return ResponseEntity<String>(HttpStatus.CREATED)
    }
}