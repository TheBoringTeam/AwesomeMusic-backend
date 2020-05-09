package com.music.awesomemusic.utils.exceptions.user

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
class TooManyAttemptsException(message: String) : Exception(message)