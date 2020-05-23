package com.music.awesomemusic.persistence.dto.request

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.NotEmpty

data class AccountLoginForm @JsonCreator constructor(
        @field:JsonProperty("login")
        @field:NotEmpty(message = "Login could not be empty")
        private val loginField: String?,

        @field:JsonProperty("password")
        @field:NotEmpty(message = "Password could not be empty")
        private val passwordField: String?
) {
    val login: String
        @JsonIgnore
        get() = loginField!!

    val password: String
        @JsonIgnore
        get() = passwordField!!
}