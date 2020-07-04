package com.music.awesomemusic.persistence.dto.request

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.music.awesomemusic.utils.validators.annotations.ExistsEmail
import javax.validation.constraints.NotEmpty

data class ResetPasswordForm @JsonCreator constructor(
        @JsonProperty("email")
        @field:NotEmpty(message = "Email field cannot be empty")
        @field:ExistsEmail
        private val emailField: String?
) {
    val email: String
        @JsonIgnore
        get() = emailField!!
}