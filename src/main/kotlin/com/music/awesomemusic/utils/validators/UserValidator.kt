package com.music.awesomemusic.utils.validators

import com.music.awesomemusic.persistence.dto.request.UserRegistrationForm
import com.music.awesomemusic.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.validation.Errors
import org.springframework.validation.ValidationUtils
import org.springframework.validation.Validator

@Component
class UserValidator : Validator {

    @Autowired
    lateinit var userService: UserService

    val emailValidator: EmailValidator = EmailValidator()


    override fun validate(target: Any, errors: Errors) {
        val user = target as UserRegistrationForm

        // validate empty username
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty")

        // validate username
        if (user.username.length < 6) {
            errors.rejectValue("username", "Length", "Username is too short")
        }
        if (user.username.length > 32) {
            errors.rejectValue("username", "Length", "Username is too long")
        }

        // validate if username exists
        if (userService.existsByUsername(user.username)) {
            errors.rejectValue("username", "Exists", "User with this username already exists")
        }

        // validate empty email
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty")

        // validate email
        if (!emailValidator.isValidPattern(email = user.email)) {
            errors.rejectValue("email","Format", "Email format is not correct")
        }

        //validate if email exists
        if (userService.existsByEmail(user.email)) {
            errors.rejectValue("email", "Exists", "User with this email already exists")
        }

        //validate empty pass
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty")

        // password validation
        if (user.password.length < 5) {
            errors.rejectValue("password", "Length", "Password is too short")
        }
        if (user.password.length > 32) {
            errors.rejectValue("password", "Length", "Password is too long")
        }

    }

    override fun supports(clazz: Class<*>): Boolean {
        return UserRegistrationForm::class.java == clazz
    }

}