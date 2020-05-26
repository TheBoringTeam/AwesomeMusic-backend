package com.music.awesomemusic.controllers


import com.music.awesomemusic.persistence.domain.AwesomeAccount
import com.music.awesomemusic.persistence.domain.VerificationToken
import com.music.awesomemusic.persistence.dto.request.*
import com.music.awesomemusic.persistence.dto.response.BadRequestResponse
import com.music.awesomemusic.security.tokens.JwtTokenProvider
import com.music.awesomemusic.services.AccountService
import com.music.awesomemusic.services.TokenService
import com.music.awesomemusic.utils.events.OnPasswordResetEvent
import com.music.awesomemusic.utils.events.OnRegistrationCompleteEvent
import com.music.awesomemusic.utils.exceptions.basic.ResourceNotFoundException
import com.music.awesomemusic.utils.exceptions.basic.WrongArgumentsException
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
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.WebRequest
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid


/**
 * Controller for account management.
 */
@RestController
@RequestMapping("api/user")
class AccountController {

    private val _logger = Logger.getLogger(AccountController::class.java)

    @Autowired
    lateinit var authenticationManager: AuthenticationManager

    @Autowired
    lateinit var accountService: AccountService

    @Autowired
    lateinit var tokenService: TokenService

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
    fun register(@Valid @RequestBody accountSignUpForm: AccountSignUpForm, bindingResult: BindingResult,
                 request: HttpServletRequest): ResponseEntity<*> {
        _logger.debug("Start register process")

        if (bindingResult.hasErrors()) {
            throw WrongArgumentsException(bindingResult.allErrors[0].defaultMessage)
        }

        val account = accountService.createAccount(accountSignUpForm)

        _logger.debug("User was created")

        // send registration mail via event
        applicationEventPublisher.publishEvent(OnRegistrationCompleteEvent(account, request.locale, request.contextPath))

        return ResponseEntity<String>(HttpStatus.CREATED)
    }

    @PostMapping("/sign-in")
    fun signIn(@RequestBody(required = true) @Valid accountLoginForm: AccountLoginForm, bindingResult: BindingResult): ResponseEntity<*> {
        _logger.debug("Start sign in process")

        if (bindingResult.hasErrors()) {
            throw WrongArgumentsException(bindingResult.allErrors[0].defaultMessage)
        }

        // authenticate user
        // Possibly could be optimized. Probably it's possible to get user from authentication object
        authenticationManager.authenticate(UsernamePasswordAuthenticationToken(accountLoginForm.login, accountLoginForm.password))


        //get authenticated user
        val user: AwesomeAccount = try { // try to find by email
            accountService.findByEmail(accountLoginForm.login)
        } catch (e: ResourceNotFoundException) { // if doesn't exists, then find by username
            accountService.findByUsername(accountLoginForm.login)
        }

        val authorities = arrayListOf<String>()

        // TODO: Attach authorises to user

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
        val verificationToken: VerificationToken

        try {
            verificationToken = tokenService.getEmailVerificationToken(token)
        } catch (e: ResourceNotFoundException) { // if token is not present in database
            val message = messages.getMessage("auth.message.invalid", null, locale)
            // TODO : Redirect to front end
            return ResponseEntity<String>(message, HttpStatus.OK)
        }

        val account = verificationToken.account

        if (tokenService.isTokenExpired(verificationToken)) { // if token is expired
            val message = messages.getMessage("auth.message.expired", null, locale)
            // TODO : Redirect to front end
            return ResponseEntity<String>(message, HttpStatus.OK)
        }

        account.isActivated = true
        accountService.saveAccount(account)

        // TODO: Redirect to front-end
        return ResponseEntity<String>("Here should be redirect to frontend", HttpStatus.OK)
    }

    @PostMapping("/resetPassword")
    fun resetPassword(@RequestBody(required = true) @Valid resetPasswordForm: ResetPasswordForm, bindingResult: BindingResult,
                      request: HttpServletRequest): ResponseEntity<*> {
        //form validation
        if (bindingResult.hasErrors()) {
            throw WrongArgumentsException(bindingResult.allErrors[0].defaultMessage)
        }

        val account = accountService.findByEmail(resetPasswordForm.email)

        applicationEventPublisher.publishEvent(OnPasswordResetEvent(account, request.locale, request.contextPath))
        return ResponseEntity<String>(HttpStatus.OK)
    }

    @PostMapping("/resetPasswordConfirm")
    fun confirmResetPassword(@RequestBody @Valid resetPasswordConfirmForm: ResetPasswordConfirmForm, bindingResult: BindingResult,
                             request: HttpServletRequest): ResponseEntity<*> {
        if (bindingResult.hasErrors()) {
            throw WrongArgumentsException(bindingResult.allErrors[0].defaultMessage)
        }

        val verificationToken: VerificationToken
        try {
            verificationToken = tokenService.getResetPasswordToken(resetPasswordConfirmForm.token)
        } catch (e: ResourceNotFoundException) { // if token is not present in database
            val message = messages.getMessage("auth.message.invalid", null, request.locale)
            // TODO : Redirect to front end
            return ResponseEntity<String>(message, HttpStatus.OK)
        }

        val account = verificationToken.account
        if (tokenService.isTokenExpired(verificationToken)) { // if token is expired
            val message = messages.getMessage("auth.message.expired", null, request.locale)
            // TODO : Redirect to front end
            return ResponseEntity<String>(message, HttpStatus.OK)
        }

        accountService.setPassword(account, resetPasswordConfirmForm.password)
        tokenService.delete(verificationToken)
        return ResponseEntity.ok("Password was reset")
    }

    @PutMapping("/changePassword")
    fun changePassword(@RequestBody(required = true) @Valid changePasswordForm: ChangePasswordForm, bindingResult: BindingResult,
                       request: HttpServletRequest, @AuthenticationPrincipal userDetails: UserDetails): ResponseEntity<*> {
        //form validation
        if (bindingResult.hasErrors()) {
            throw WrongArgumentsException(bindingResult.allErrors[0].defaultMessage)
        }

        val account = accountService.findByUsername(userDetails.username)
        if (!accountService.isPasswordEquals(changePasswordForm.oldPassword, account)) {
            return ResponseEntity.badRequest().body(BadRequestResponse("Old password is not correct", request.servletPath))
        }

        accountService.setPassword(account, changePasswordForm.newPassword)

        return ResponseEntity.ok().body("Password was successfully changed")
    }
}