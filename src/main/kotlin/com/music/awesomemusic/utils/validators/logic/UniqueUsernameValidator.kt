package com.music.awesomemusic.utils.validators.logic

import com.music.awesomemusic.services.AccountService
import com.music.awesomemusic.utils.exceptions.basic.WrongArgumentsException
import com.music.awesomemusic.utils.validators.annotations.UniqueUsername
import org.springframework.beans.factory.annotation.Autowired
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class UniqueUsernameValidator : ConstraintValidator<UniqueUsername, String> {

    @Autowired
    lateinit var accountService: AccountService

    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        if (value == null) {
            throw WrongArgumentsException("Username could not be empty")
        } else {
            return !accountService.existsByUsername(value)
        }
    }
}