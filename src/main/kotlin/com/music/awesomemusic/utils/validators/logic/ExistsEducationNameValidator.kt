package com.music.awesomemusic.utils.validators.logic

import com.music.awesomemusic.persistence.domain.Education
import com.music.awesomemusic.utils.validators.annotations.ExistsEducation
import org.apache.log4j.Logger
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class ExistsEducationNameValidator : ConstraintValidator<ExistsEducation, String> {

    private val _logger = Logger.getLogger(ExistsEducationNameValidator::class.java)

    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        value?.let { educationName ->
            return Education.values().any { it.name == educationName.toUpperCase() }
        }
        return true
    }
}