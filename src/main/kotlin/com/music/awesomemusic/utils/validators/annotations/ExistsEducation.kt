package com.music.awesomemusic.utils.validators.annotations

import com.music.awesomemusic.utils.validators.logic.ExistsEducationNameValidator
import javax.validation.Constraint
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [ExistsEducationNameValidator::class])
annotation class ExistsEducation(
        val message: String = "Wrong education type",
        val groups: Array<KClass<out Any>> = [],
        val payload: Array<KClass<out Any>> = []
)