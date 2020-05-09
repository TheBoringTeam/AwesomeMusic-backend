package com.music.awesomemusic.utils.exceptions.user

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class AccountNotFoundException(msg: String) : Exception(msg) {

}