package com.music.awesomemusic.utils.validators.logic

import com.music.awesomemusic.services.InformationService
import com.music.awesomemusic.utils.validators.annotations.ExistsCountryCode
import org.springframework.beans.factory.annotation.Autowired
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class ExistsCountryCodeValidator : ConstraintValidator<ExistsCountryCode, String> {

    @Autowired
    lateinit var informationService: InformationService

    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        value?.let {
            return informationService.countryExistsByCode(it)
        }
        return true
    }
}