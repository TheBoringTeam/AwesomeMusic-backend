package com.music.awesomemusic.utils.validators.annotations

import com.music.awesomemusic.utils.validators.logic.ExistsGenderValidator
import javax.validation.Constraint
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [ExistsGenderValidator::class])
annotation class ExistsGender(
        val message: String = "Wrong gender type",
        val groups: Array<KClass<out Any>> = [],
        val payload: Array<KClass<out Any>> = []
)