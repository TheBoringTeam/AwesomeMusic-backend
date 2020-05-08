package com.music.awesomemusic.utils.advices

import com.music.awesomemusic.utils.errors.TooManyAttemptsException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class UserServiceAdvice {

    @ExceptionHandler(TooManyAttemptsException::class)
    fun handleTooManyAttemptsException(e: TooManyAttemptsException): ResponseEntity<*> {
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(e.message)
    }
}