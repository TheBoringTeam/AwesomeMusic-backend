package com.music.awesomemusic.persistence.dto.response

import com.fasterxml.jackson.annotation.JsonProperty

data class LanguageResponse(
        @JsonProperty("name")
        val languageName: String,

        @JsonProperty("code")
        val languageCode: String
)