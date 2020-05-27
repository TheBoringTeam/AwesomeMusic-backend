package com.music.awesomemusic.persistence.dto.request

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.music.awesomemusic.persistence.domain.Education
import com.music.awesomemusic.persistence.domain.Gender
import com.music.awesomemusic.persistence.domain.Language
import java.util.*

data class UpdateAccountForm @JsonCreator constructor(
        @JsonProperty("username")
        val username: String?,

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
        val education: Education?,

        @JsonProperty("country_id")
        val language: Language?
)