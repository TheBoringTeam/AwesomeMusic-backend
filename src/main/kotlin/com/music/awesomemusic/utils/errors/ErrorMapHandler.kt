package com.music.awesomemusic.utils.errors

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class ErrorMapHandler {
    private val _errorMap = HashMap<String, String?>()

    fun addToErrorMap(error: String?) = apply {
        this._errorMap["error"] = error
    }

    fun errorToJSON(statusCode: HttpStatus): ResponseEntity<Map<String, String?>> {
        return ResponseEntity<Map<String, String?>>(_errorMap, statusCode)
    }
}