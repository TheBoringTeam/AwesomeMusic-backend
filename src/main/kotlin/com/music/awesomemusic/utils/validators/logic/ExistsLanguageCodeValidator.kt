package com.music.awesomemusic.utils.validators.logic

import com.music.awesomemusic.services.InformationService
import com.music.awesomemusic.utils.validators.annotations.ExistsLanguageCode
import org.springframework.beans.factory.annotation.Autowired
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class ExistsLanguageCodeValidator : ConstraintValidator<ExistsLanguageCode, String> {

    @Autowired
    lateinit var informationService: InformationService

    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        value?.let {
            return informationService.languageExistsByCode(it)
        }
        return true
    }
}