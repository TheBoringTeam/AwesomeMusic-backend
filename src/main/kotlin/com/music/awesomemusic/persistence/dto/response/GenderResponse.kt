package com.music.awesomemusic.persistence.dto.response

import com.fasterxml.jackson.annotation.JsonProperty

data class GenderResponse(
        @JsonProperty("gender_name")
        val genderName: String
)