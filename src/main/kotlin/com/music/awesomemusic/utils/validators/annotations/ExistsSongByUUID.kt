package com.music.awesomemusic.utils.validators.annotations

import com.music.awesomemusic.utils.validators.logic.ExistsSongOwnerValidator
import javax.validation.Constraint
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD, AnnotationTarget.FUNCTION, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [ExistsSongOwnerValidator::class])
annotation class ExistsSongByUUID(
        val message: String = "There is no song with this UUID",
        val groups: Array<KClass<out Any>> = [],
        val payload: Array<KClass<out Any>> = []
)