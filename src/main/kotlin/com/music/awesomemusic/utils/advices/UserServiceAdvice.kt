package com.music.awesomemusic.utils.advices

import com.music.awesomemusic.persistence.dto.response.ErrorResponse
import org.apache.log4j.Logger
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import javax.servlet.http.HttpServletRequest


@ControllerAdvice
class UserServiceAdvice {

    private val _logger = Logger.getLogger(UserServiceAdvice::class.java)

    @ExceptionHandler(IllegalArgumentException::class, HttpMessageNotReadableException::class)
    fun handleException(exception: Exception,
                        request: HttpServletRequest): ResponseEntity<*> {
        return ResponseEntity.badRequest().body(ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                "Bad Request", "Body cannot be empty", request.servletPath))
    }
}