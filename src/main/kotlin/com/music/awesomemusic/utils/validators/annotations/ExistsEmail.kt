package com.music.awesomemusic.utils.validators.annotations

import com.music.awesomemusic.utils.validators.logic.ExistsEmailValidator
import javax.validation.Constraint
import kotlin.reflect.KClass


@Target(AnnotationTarget.FIELD, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [ExistsEmailValidator::class])
annotation class ExistsEmail(
        val message: String = "There is no user with this email!",
        val groups: Array<KClass<out Any>> = [],
        val payload: Array<KClass<out Any>> = []
)