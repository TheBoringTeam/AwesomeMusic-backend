package com.music.awesomemusic.utils.validators.annotations

import com.music.awesomemusic.utils.validators.logic.ExistsCountryCodeValidator
import javax.validation.Constraint
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [ExistsCountryCodeValidator::class])
annotation class ExistsCountryCode(
        val message: String = "There is no country with this code",
        val groups: Array<KClass<out Any>> = [],
        val payload: Array<KClass<out Any>> = []
)