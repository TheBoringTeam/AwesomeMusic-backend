package com.music.awesomemusic.controllers


import com.music.awesomemusic.persistence.domain.AwesomeAccount
import com.music.awesomemusic.persistence.dto.request.UserRegistrationForm
import com.music.awesomemusic.persistence.dto.request.UserSignInForm
import com.music.awesomemusic.security.tokens.JwtTokenProvider
import com.music.awesomemusic.services.AccountService
import com.music.awesomemusic.utils.exceptions.basic.ResourceNotFoundException
import com.music.awesomemusic.utils.exceptions.basic.WrongArgumentsException
import com.music.awesomemusic.utils.listeners.OnRegistrationCompleteEvent
import com.music.awesomemusic.utils.other.ResponseBuilderMap
import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.MessageSource
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.WebRequest
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid


/**
 * Controller for user management.
 */
@RestController
@RequestMapping("api/user")
class UserController {

    private val _logger = Logger.getLogger(UserController::class.java)

    @Autowired
    lateinit var authenticationManager: AuthenticationManager

    @Autowired
    lateinit var accountService: AccountService

    @Autowired
    lateinit var messages: MessageSource

    @Autowired
    lateinit var jwtTokenProvider: JwtTokenProvider

    @Autowired
    lateinit var applicationEventPublisher: ApplicationEventPublisher

    @GetMapping("/hello")
    @PreAuthorize("hasAuthority('ADMIN')")
    fun hello(): ResponseEntity<*> {
        _logger.debug("Hello ending")
        return ResponseEntity<String>("Heelo", HttpStatus.OK)
    }

    @PostMapping("/registration")
    fun register(@Valid @RequestBody userRegistrationForm: UserRegistrationForm, bindingResult: BindingResult,
                 request: HttpServletRequest): ResponseEntity<*> {
        _logger.debug("Start register process")

        if (bindingResult.hasErrors()) {
            throw WrongArgumentsException(bindingResult.allErrors[0].defaultMessage)
        }

        val account = accountService.createAccount(userRegistrationForm)

        _logger.debug("User was created")

        // send registration mail via event
        applicationEventPublisher.publishEvent(OnRegistrationCompleteEvent(account, request.locale, request.contextPath))

        return ResponseEntity<String>(HttpStatus.CREATED)
    }

    @PostMapping("/sign-in")
    fun signIn(@RequestBody(required = true) userSignInForm: UserSignInForm, bindingResult: BindingResult): ResponseEntity<*> {
        _logger.debug("Start sign in process")

        // authenticate user
        // Possibly could be optimized. Probably it's possible to get user from authentication object
        authenticationManager.authenticate(UsernamePasswordAuthenticationToken(userSignInForm.login, userSignInForm.password))

        //get authenticated user
        val user: AwesomeAccount = try { // try to find by email
            accountService.findByEmail(userSignInForm.login)
        } catch (e: ResourceNotFoundException) { // if doesn't exists, then find by username
            accountService.findByUsername(userSignInForm.login)
        }

        val authorities = arrayListOf<String>()
//        user.roles.forEach { roleMapping ->
//            roleMapping.role.permissions.forEach { permission ->
//                authorities.add(permission.name)
//            }
//        }

        // return token for user
        val token = jwtTokenProvider.createToken(user.username, authorities)
        return ResponseBuilderMap().addField("token", token).toJSON()
    }

    @GetMapping("/me")
    fun me(@AuthenticationPrincipal userDetails: UserDetails): ResponseEntity<*> {
        val account = accountService.findByUsername(userDetails.username)

        return ResponseBuilderMap()
                .addField("username", userDetails.username)
                .addField("is_admin", userDetails.authorities.contains(SimpleGrantedAuthority("ADMIN")))
                .addField("email", account.email)
                .addField("is_activated", account.isActivated)
                .toJSON()
    }

    @GetMapping("/registrationConfirm")
    fun registrationConfirm(@RequestParam("token") token: String, request: WebRequest): ResponseEntity<*> {
        val locale = request.locale

        val verificationToken = accountService.getVerificationToken(token)

        if (verificationToken == null) {
            val message = messages.getMessage("auth.message.invalid", null, locale)

            // TODO : Redirect to front end
            return ResponseEntity<String>(message, HttpStatus.OK)
        }

        val account = verificationToken.account

        val cal = Calendar.getInstance()

        if (verificationToken.expiryDate.time - cal.time.time <= 0) {
            val message = messages.getMessage("auth.message.expired", null, locale)
            // TODO : Redirect to front end
            return ResponseEntity<String>(message, HttpStatus.OK)
        }

        account.isActivated = true
        accountService.saveAccount(account)

        // TODO: Redirect to front-end
        return ResponseEntity<String>("Fine", HttpStatus.OK)
    }
}