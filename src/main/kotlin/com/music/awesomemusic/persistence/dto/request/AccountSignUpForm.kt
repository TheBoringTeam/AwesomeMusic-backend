package com.music.awesomemusic.persistence.dto.request

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.music.awesomemusic.utils.validators.annotations.UniqueEmail
import javax.validation.constraints.*


class AccountSignUpForm @JsonCreator constructor(
        @field:JsonProperty("login")
        @field:NotEmpty(message = "Login could not be empty")
        @field:Size(message = "Login should be from 6 to 32 length", min = 5, max = 32)
        private val loginField: String?,

        @field:JsonProperty("password")
        @field:NotEmpty(message = "Password could not be empty")
        @field:Size(message = "Password should be from 6 to 32 length", min = 6, max = 32)
        private val passwordField: String?,

        @field:JsonProperty("email")
        @field:NotEmpty(message = "Email could not be empty")
        @field:Email(message = "Email should be properly formatted")
        @field:UniqueEmail(message = "User with this email already exists")
        private val emailField: String?,

        @field:JsonProperty("is_collective")
        @field:NotNull(message = "Collective could not be null")
        private val isCollectiveField: Boolean?
) {
    val login: String
        get() = loginField!!

    val password: String
        get() = passwordField!!

    val isCollective: Boolean
        get() = isCollectiveField!!

    val email: String
        get() = emailField!!
}