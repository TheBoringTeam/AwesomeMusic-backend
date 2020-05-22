package com.music.awesomemusic.utils.validators.annotations

import com.music.awesomemusic.utils.validators.logic.UniqueUsernameValidator
import javax.validation.Constraint
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [UniqueUsernameValidator::class])
annotation class UniqueUsername(
        val message: String = "There is already user with this username!",
        val groups: Array<KClass<out Any>> = [],
        val payload: Array<KClass<out Any>> = []
)