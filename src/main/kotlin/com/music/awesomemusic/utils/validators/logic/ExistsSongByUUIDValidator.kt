package com.music.awesomemusic.utils.validators.logic

import com.music.awesomemusic.services.SongService
import com.music.awesomemusic.utils.validators.annotations.ExistsSongByUUID
import org.springframework.beans.factory.annotation.Autowired
import java.util.*
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class ExistsSongByUUIDValidator : ConstraintValidator<ExistsSongByUUID, UUID> {

    @Autowired
    private lateinit var _songService: SongService

    override fun isValid(value: UUID, context: ConstraintValidatorContext?): Boolean {
        return _songService.existsByUUID(value)
    }
}