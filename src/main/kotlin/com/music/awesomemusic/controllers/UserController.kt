package com.music.awesomemusic.controllers

import com.music.awesomemusic.domain.dto.UserRegistrationForm
import com.music.awesomemusic.domain.dto.UserSignInForm
import com.music.awesomemusic.models.AwesomeUser
import com.music.awesomemusic.security.JwtTokenProvider
import com.music.awesomemusic.services.AwesomeUserDetailsService
import com.music.awesomemusic.services.UserService
import com.music.awesomemusic.utils.errors.MapValidationErrorService
import com.music.awesomemusic.utils.validators.UserValidator
import org.apache.log4j.Logger
import org.apache.tomcat.jni.User.username
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import java.util.*
import java.util.stream.Collectors.toList
import kotlin.collections.HashMap
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType


@RestController
@RequestMapping("api/user")
class UserController {

    private val logger = Logger.getLogger(UserController::class.java)

    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var authenticationManager: AuthenticationManager

    @Autowired
    lateinit var mapValidator: MapValidationErrorService

    @Autowired
    lateinit var userValidator: UserValidator

    @Autowired
    lateinit var jwtTokenProvider: JwtTokenProvider

    @GetMapping("/{id}")
    fun info(@PathVariable id: Long): ResponseEntity<*> {
        val user = userService.findById(id)
        return ResponseEntity<AwesomeUser>(user, HttpStatus.OK)
    }

    @GetMapping("/hello")
    @PreAuthorize("hasRole('ADMIN')")
    fun hello(): ResponseEntity<*> {
        logger.debug("Hello ending")
        return ResponseEntity<String>("Heelo", HttpStatus.OK)
    }

    @PostMapping("/register")
    fun register(@RequestBody(required = true) userRegistrationForm: UserRegistrationForm, bindingResult: BindingResult): ResponseEntity<*> {
        logger.debug("Start register process")
        userValidator.validate(userRegistrationForm, bindingResult)
        val errorMap = mapValidator.createErrorMap(bindingResult)

        if (errorMap != null) {
            logger.error("Registration validation failed")
            return errorMap
        }

        userService.createUser(userRegistrationForm)
        logger.debug("User was created")
        return ResponseEntity<String>(HttpStatus.CREATED)
    }

    @PostMapping("/sign-in")
    fun signIn(@RequestBody(required = true) userSignInForm: UserSignInForm, bindingResult: BindingResult): ResponseEntity<*> {
        logger.debug("Start sign in process")

        try {
            authenticationManager.authenticate(UsernamePasswordAuthenticationToken(userSignInForm.username, userSignInForm.password))
            // TODO : Continue here
            val user = userService.findByUsername(userSignInForm.username)
            if (user != null) {
                val token = jwtTokenProvider.createToken(user.username, user.roles.map { it.roleName })
                return ResponseEntity<String>(token, HttpStatus.OK)
            }
            return ResponseEntity<String>(HttpStatus.UNAUTHORIZED)
        } catch (e: Exception) {
            logger.error("Fuck this")
            throw e
        }
    }

    @GetMapping("/me")
    fun me(@AuthenticationPrincipal userDetails: UserDetails): ResponseEntity<*> {
        val model = HashMap<String, Any>()
        model["username"] = userDetails.username
        model["is_admin"] = userDetails.authorities.contains(SimpleGrantedAuthority("ADMIN"))
        return ResponseEntity.ok(model)
    }
}