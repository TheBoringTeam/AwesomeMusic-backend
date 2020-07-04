package com.music.awesomemusic.persistence.dto.response

import java.sql.Timestamp
import java.util.*

open class ErrorResponse(
        val status: Int,
        val error: String,
        val message: String,
        val path: String
) {
    val timestamp: Timestamp = Timestamp(Date().time)
}