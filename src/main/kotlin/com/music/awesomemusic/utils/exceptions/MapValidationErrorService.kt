package com.music.awesomemusic.utils.errors

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.validation.BindingResult

@Service
class MapValidationErrorService {
    fun createErrorMap(result: BindingResult): ResponseEntity<*>? {

        if (result.hasErrors()) {
            val errorMap = HashMap<String, String?>()
            for (error in result.fieldErrors) {
                errorMap[error.field] = error.defaultMessage
            }
            return ResponseEntity<Map<String, String?>>(errorMap, HttpStatus.BAD_REQUEST)

        }

        return null
    }
}