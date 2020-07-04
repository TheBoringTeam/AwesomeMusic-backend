package com.music.awesomemusic.persistence.dto.request

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.NotEmpty

data class ChangePasswordForm @JsonCreator constructor(
        @JsonProperty("old_password")
        @field:NotEmpty(message = "Old password cannot be empty")
        private val oldPasswordField: String?,

        @JsonProperty("new_password")
        @field:NotEmpty(message = "New password cannot be empty")
        private val newPasswordField: String?
) {
    val oldPassword: String
        get() = oldPasswordField!!

    val newPassword: String
        get() = newPasswordField!!
}