package com.music.awesomemusic.utils.validators.logic

import com.music.awesomemusic.persistence.domain.Gender
import com.music.awesomemusic.utils.validators.annotations.ExistsGender
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class ExistsGenderValidator : ConstraintValidator<ExistsGender, String> {

    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        value?.let { genderName ->
            return Gender.values().any { it.name == genderName.toUpperCase() }
        }
        return true
    }
}