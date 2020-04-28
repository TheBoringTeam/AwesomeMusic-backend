package com.music.awesomemusic.utils.validators

import java.util.regex.Pattern

/**
 * Validates email based on regular expression.
 */
class EmailValidator {
    private val _regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
    private val _pattern = Pattern.compile(_regex)

    fun isValidPattern(email: String): Boolean {
        if (email.length > 64) {
            return false
        }

        return _pattern.matcher(email).matches()
    }
}