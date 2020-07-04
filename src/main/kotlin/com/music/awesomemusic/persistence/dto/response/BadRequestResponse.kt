package com.music.awesomemusic.persistence.dto.response

import org.springframework.http.HttpStatus
import java.sql.Timestamp
import java.util.*

class BadRequestResponse(val message: String, val path: String) {
    val timestamp: Timestamp = Timestamp(Date().time)
    val status: Int = HttpStatus.BAD_REQUEST.value()
    val error = "Bad Request"
}