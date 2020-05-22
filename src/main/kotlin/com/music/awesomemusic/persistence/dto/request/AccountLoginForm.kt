package com.music.awesomemusic.persistence.dto.request

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.NotEmpty

data class AccountLoginForm(
        @field:JsonProperty("login")
        @field:NotEmpty(message = "Login could not be empty")
        val login: String,

        @field:JsonProperty("password")
        @field:NotEmpty(message = "Password could not be empty")
        val password: String)