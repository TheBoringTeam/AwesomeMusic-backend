package com.music.awesomemusic.utils.exceptions.basic

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
class WrongArgumentsException(msg : String?) : Exception(msg)