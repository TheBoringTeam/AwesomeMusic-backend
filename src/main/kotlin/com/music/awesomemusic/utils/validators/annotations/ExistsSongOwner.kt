package com.music.awesomemusic.utils.validators.annotations

import com.music.awesomemusic.utils.validators.logic.ExistsLanguageCodeValidator
import com.music.awesomemusic.utils.validators.logic.ExistsSongOwnerValidator
import javax.validation.Constraint
import kotlin.reflect.KClass


@Target(AnnotationTarget.FIELD, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [ExistsSongOwnerValidator::class])
annotation class ExistsSongOwner(
        val message: String = "There is no song owner with this id",
        val groups: Array<KClass<out Any>> = [],
        val payload: Array<KClass<out Any>> = []
)