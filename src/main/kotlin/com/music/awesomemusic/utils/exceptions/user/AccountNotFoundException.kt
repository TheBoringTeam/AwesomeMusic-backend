package com.music.awesomemusic.utils.exceptions.user

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.FORBIDDEN)
class AccountNotFoundException(msg: String) : Exception(msg) {

}