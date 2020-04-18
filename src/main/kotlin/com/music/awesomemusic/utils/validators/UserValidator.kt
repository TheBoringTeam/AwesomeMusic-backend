package com.music.awesomemusic.utils.validators

import com.music.awesomemusic.domain.dto.UserRegistrationForm
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


    override fun validate(target: Any, errors: Errors) {
        val user = target as UserRegistrationForm

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty")

        if (user.username.length < 6) {
            errors.rejectValue("username", "Length", "Username is too short")
        }

        if (user.username.length > 32) {
            errors.rejectValue("username", "Length", "Username is too long")
        }


        if (userService.existsByUsername(user.username)) {
            errors.rejectValue("username", "Username is already taken")
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty")

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