package com.music.awesomemusic.utils.validators.logic

import com.music.awesomemusic.services.InformationService
import com.music.awesomemusic.utils.validators.annotations.ExistsSongOwner
import org.springframework.beans.factory.annotation.Autowired
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class ExistsSongOwnerValidator : ConstraintValidator<ExistsSongOwner, Long> {

    @Autowired
    lateinit var informationService: InformationService

    override fun isValid(value: Long?, context: ConstraintValidatorContext?): Boolean {
        value?.let {
            return informationService.existsSongOwnerById(it)
        }
        return true
    }
}