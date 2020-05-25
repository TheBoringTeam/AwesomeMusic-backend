package com.music.awesomemusic.persistence.dto.request

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size

data class ResetPasswordConfirmForm @JsonCreator constructor(
        @field:JsonProperty("new_password")
        @field:NotEmpty(message = "New password could not be empty")
        @field:Size(message = "New password should be from 6 to 32 length", min = 6, max = 32)
        private val passwordField: String?,

        @field:JsonProperty("token")
        @field:NotEmpty(message = "Token cannot be empty")
        private val tokenField: String?
) {
    val password: String
        get() = passwordField!!

    val token: String
        get() = tokenField!!
}