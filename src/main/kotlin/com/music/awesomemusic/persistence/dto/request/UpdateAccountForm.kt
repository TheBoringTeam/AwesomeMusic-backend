package com.music.awesomemusic.persistence.dto.request

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.music.awesomemusic.persistence.domain.Education
import com.music.awesomemusic.persistence.domain.Gender
import com.music.awesomemusic.utils.validators.annotations.ExistsCountryCode
import com.music.awesomemusic.utils.validators.annotations.ExistsEducation
import com.music.awesomemusic.utils.validators.annotations.ExistsLanguageCode
import java.util.*

data class UpdateAccountForm @JsonCreator constructor(
        @JsonProperty("name")
        val name: String?,

        @JsonProperty("biography")
        val biography: String?,

        @JsonProperty("birthday")
        val birthday: Date?,

        @JsonProperty("deathday")
        val deathday: Date?,

        @JsonProperty("gender")
        val gender: Gender?,

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