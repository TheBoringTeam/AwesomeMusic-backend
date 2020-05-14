package com.music.awesomemusic.utils.exceptions.user

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.FORBIDDEN)
class AccountNotActivatedException(msg: String) : Exception(msg)