package com.music.awesomemusic.persistence.dto.request

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.*

data class UserRegistrationForm(
        @field:NotEmpty(message = "Username could not be empty")
        @field:NotBlank(message = "Username could not be empty")
        @field:Min(6, message = "Username must be at least 6 chars length")
        @field:Max(32)
        val username: String,

        @NotEmpty
        @Size(min = 5, max = 32)
        @JsonProperty(value = "password")
        val password: String,

        @NotEmpty
        @JsonProperty(value = "email")
        val email: String,

        @NotEmpty
        @JsonProperty(value = "is_collective")
        val isCollective: Boolean
)