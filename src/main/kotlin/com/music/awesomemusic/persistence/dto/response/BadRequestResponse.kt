package com.music.awesomemusic.persistence.dto.response

import org.springframework.http.HttpStatus

class BadRequestResponse(val msg: String, val errorPath: String) : ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Bad Request", msg, errorPath){

}