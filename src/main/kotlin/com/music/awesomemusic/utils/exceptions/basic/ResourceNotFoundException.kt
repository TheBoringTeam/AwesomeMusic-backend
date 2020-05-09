package com.music.awesomemusic.utils.exceptions.basic

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
class ResourceNotFoundException(msg: String) : Exception(msg)