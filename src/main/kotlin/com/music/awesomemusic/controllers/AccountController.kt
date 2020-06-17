package com.music.awesomemusic.controllers


import com.music.awesomemusic.persistence.domain.AwesomeAccount
import com.music.awesomemusic.persistence.domain.VerificationToken
import com.music.awesomemusic.persistence.dto.request.*
import com.music.awesomemusic.persistence.dto.response.AccountResponse
import com.music.awesomemusic.persistence.dto.response.BadRequestResponse
import com.music.awesomemusic.persistence.dto.response.BasicStringResponse
import com.music.awesomemusic.security.tokens.JwtTokenProvider
import com.music.awesomemusic.services.AccountService
import com.music.awesomemusic.services.FileStorageService
import com.music.awesomemusic.services.TokenService
import com.music.awesomemusic.utils.events.OnPasswordResetEvent
import com.music.awesomemusic.utils.events.OnRegistrationCompleteEvent
import com.music.awesomemusic.utils.exceptions.basic.ResourceNotFoundException
import com.music.awesomemusic.utils.exceptions.basic.WrongArgumentsException
import com.music.awesomemusic.utils.other.ResponseBuilderMap
import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.MessageSource
import org.springframework.http.*
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.WebRequest
import org.springframework.web.multipart.MultipartFile
import java.net.URI
import java.util.*
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
    private lateinit var _accountService: AccountService

    @Autowired
    lateinit var tokenService: TokenService

    @Autowired
    private lateinit var _storageService: FileStorageService

    @Autowired
    lateinit var messages: MessageSource

    @Autowired
    lateinit var jwtTokenProvider: JwtTokenProvider

    @Autowired
    lateinit var applicationEventPublisher: ApplicationEventPublisher

    @Value("\${spring.mail.ip}")
    lateinit var serverIp: String

    @GetMapping("/hello")
    @PreAuthorize("hasAuthority('ADMIN')")
    fun hello(): ResponseEntity<*> {
        _logger.debug("Hello ending")
        return ResponseEntity<String>("Heelo", HttpStatus.OK)
    }

    @PostMapping("/registration")
    @ResponseBody
    fun register(@Valid @RequestBody accountSignUpForm: AccountSignUpForm, bindingResult: BindingResult,
                 request: HttpServletRequest): ResponseEntity<*> {
        _logger.debug("Start register process")

        if (bindingResult.hasErrors()) {
            throw WrongArgumentsException(bindingResult.allErrors[0].defaultMessage)
        }

        val account = _accountService.createAccount(accountSignUpForm)

        _logger.debug("User was created")

        // send registration mail via event
        applicationEventPublisher.publishEvent(OnRegistrationCompleteEvent(account, request.locale, request.contextPath))

        return ResponseEntity.status(HttpStatus.CREATED).body(BasicStringResponse("Account was successfully created"))
    }

    @PostMapping("/sign-in")
    @ResponseBody
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
            _accountService.findByEmail(accountLoginForm.login)
        } catch (e: ResourceNotFoundException) { // if doesn't exists, then find by username
            _accountService.findByUsername(accountLoginForm.login)
        }

        val authorities = arrayListOf<String>()

        // TODO: Attach authorises to user

        // return token for user
        val token = jwtTokenProvider.createToken(user.username, authorities)
        return ResponseBuilderMap().addField("token", token).toJSON()
    }

    @GetMapping("/me")
    @ResponseBody
    fun me(@AuthenticationPrincipal userDetails: UserDetails): ResponseEntity<*> {
        val account = _accountService.findByUsername(userDetails.username)

        return ResponseBuilderMap()
                .addField("username", userDetails.username)
                .addField("is_admin", userDetails.authorities.contains(SimpleGrantedAuthority("ADMIN")))
                .addField("email", account.email)
                .addField("id", account.uuid)
                .addField("is_activated", account.isActivated)
                .toJSON()
    }

    @GetMapping("/registration-confirm")
    fun registrationConfirm(@RequestParam("token") token: String, request: WebRequest): ResponseEntity<*> {
        val locale = request.locale
        val verificationToken: VerificationToken
        val headers = HttpHeaders()

        try {
            verificationToken = tokenService.getEmailVerificationToken(token)
        } catch (e: ResourceNotFoundException) { // if token is not present in database
            val message = messages.getMessage("auth.message.invalid", null, locale)
            // Redirect to front-end invalid token page
            headers.location = URI.create("http://$serverIp/registration-invalid")
            return ResponseEntity<Unit>(headers, HttpStatus.MOVED_PERMANENTLY)
        }

        val account = verificationToken.account

        if (tokenService.isTokenExpired(verificationToken)) { // if token is expired
            val message = messages.getMessage("auth.message.expired", null, locale)
            // Redirect to front-end expired token page
            headers.location = URI.create("http://$serverIp/registration-expired")
            return ResponseEntity<Unit>(headers, HttpStatus.MOVED_PERMANENTLY)
        }

        account.isActivated = true
        _accountService.saveAccount(account)

        // Redirect to front-end success page
        headers.location = URI.create("http://$serverIp/registration-confirmed")
        return ResponseEntity<String>(headers, HttpStatus.MOVED_PERMANENTLY)
    }

    @PostMapping("/reset-password")
    @ResponseBody
    fun resetPassword(@RequestBody(required = true) @Valid resetPasswordForm: ResetPasswordForm, bindingResult: BindingResult,
                      request: HttpServletRequest): ResponseEntity<*> {
        //form validation
        if (bindingResult.hasErrors()) {
            throw WrongArgumentsException(bindingResult.allErrors[0].defaultMessage)
        }

        val account = _accountService.findByEmail(resetPasswordForm.email)

        applicationEventPublisher.publishEvent(OnPasswordResetEvent(account, request.locale, request.contextPath))
        return ResponseEntity.ok(BasicStringResponse("Reset password order was accepted"))
    }

    @PostMapping("/reset-password-confirm")
    @ResponseBody
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

        _accountService.setPassword(account, resetPasswordConfirmForm.password)
        tokenService.delete(verificationToken)
        return ResponseEntity.ok(BasicStringResponse("Password was successfully reset"))
    }

    @PutMapping("/change-password")
    @ResponseBody
    fun changePassword(@RequestBody(required = true) @Valid changePasswordForm: ChangePasswordForm, bindingResult: BindingResult,
                       request: HttpServletRequest, @AuthenticationPrincipal userDetails: UserDetails): ResponseEntity<*> {
        //form validation
        if (bindingResult.hasErrors()) {
            throw WrongArgumentsException(bindingResult.allErrors[0].defaultMessage)
        }

        val account = _accountService.findByUsername(userDetails.username)
        if (!_accountService.isPasswordEquals(changePasswordForm.oldPassword, account)) {
            return ResponseEntity.badRequest().body(BadRequestResponse("Old password is not correct", request.servletPath))
        }

        _accountService.setPassword(account, changePasswordForm.newPassword)

        return ResponseEntity.ok().body(BasicStringResponse("Password was successfully changed"))
    }

    @PutMapping("/update")
    @ResponseBody
    fun updateAccount(@RequestBody @Valid updateAccountForm: UpdateAccountForm, bindingResult: BindingResult,
                      request: HttpServletRequest, @AuthenticationPrincipal userDetails: UserDetails): ResponseEntity<*> {
        //form validation
        if (bindingResult.hasErrors()) {
            throw WrongArgumentsException(bindingResult.allErrors[0].defaultMessage)
        }

        // update account
        val account = _accountService.findByUsername(userDetails.username)
        _accountService.updateAll(updateAccountForm, account)

        return ResponseEntity.ok(BasicStringResponse("Account was successfully update"))
    }

    @PutMapping("/update-avatar")
    fun updateAvatar(@RequestPart("avatar", required = true) imageFile: MultipartFile, @AuthenticationPrincipal userDetails: UserDetails): ResponseEntity<*> {
        val account = _accountService.findByUsername(userDetails.username)
        //TODO: Test this shit
        val fileName = _storageService.saveImage(imageFile, account.uuid)
        return ResponseEntity.ok(BasicStringResponse(fileName))
    }

    @GetMapping("/{uuid}/avatar", produces = [MediaType.IMAGE_JPEG_VALUE])
    fun getProfileImage(@PathVariable("uuid") uuid: String): ResponseEntity<*> {

        val account = _accountService.findByUUID(UUID.fromString(uuid))
        val inputStream = _storageService.getImage("${account.uuid}.jpg")

        // prepare headers for response
        val headers = HttpHeaders()
        headers.cacheControl = CacheControl.noCache().headerValue
        return ResponseEntity<ByteArray>(inputStream.readAllBytes(), headers, HttpStatus.OK)
    }

    @GetMapping("/{uuid}/info")
    @ResponseBody
    fun getProfileInformation(@PathVariable("uuid") accountUUID: String,
                              @AuthenticationPrincipal userDetails: UserDetails): ResponseEntity<*> {
        val account = _accountService.findByUUID(uuid = UUID.fromString(accountUUID))
        return ResponseEntity.ok(AccountResponse(account))
    }
}