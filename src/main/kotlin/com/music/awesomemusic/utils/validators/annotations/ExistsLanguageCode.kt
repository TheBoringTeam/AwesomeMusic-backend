package com.music.awesomemusic.utils.validators.annotations

import com.music.awesomemusic.utils.validators.logic.ExistsLanguageCodeValidator
import javax.validation.Constraint
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [ExistsLanguageCodeValidator::class])
annotation class ExistsLanguageCode(
        val message: String = "There is no language with this code",
        val groups: Array<KClass<out Any>> = [],
        val payload: Array<KClass<out Any>> = []
)