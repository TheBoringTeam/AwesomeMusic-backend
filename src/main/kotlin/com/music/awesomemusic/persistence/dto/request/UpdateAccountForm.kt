package com.music.awesomemusic.persistence.dto.request

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.music.awesomemusic.utils.validators.annotations.ExistsCountryCode
import com.music.awesomemusic.utils.validators.annotations.ExistsEducation
import com.music.awesomemusic.utils.validators.annotations.ExistsGender
import com.music.awesomemusic.utils.validators.annotations.ExistsLanguageCode
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

data class UpdateAccountForm @JsonCreator constructor(
        @JsonProperty("name")
        @field:Size(max = 255, message = "Name can be max 255 chars long")
        val name: String?,

        @JsonProperty("biography")
        @field:Size(max = 1024, message = "Biography can be max 1024 chars long")
        val biography: String?,

        @JsonProperty("birthday")
        @field:Pattern(regexp = "^\\d{2}/\\d{2}/\\d{4}\$", message = "Invalid birth date format")
        val birthday: String?,

        @JsonProperty("deathday")
        @field:Pattern(regexp = "^\\d{2}/\\d{2}/\\d{4}\$", message = "Invalid death date format")
        val deathday: String?,

        @JsonProperty("gender")
        @ExistsGender
        val gender: String?,

        @JsonProperty("education")
        @ExistsEducation
        val education: String?,

        @JsonProperty("language_code")
        @ExistsLanguageCode
        val language: String?,

        @JsonProperty("country_code")
        @ExistsCountryCode
        val country: String?
)