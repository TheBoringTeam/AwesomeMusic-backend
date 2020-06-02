package com.music.awesomemusic.persistence.dto.response

import com.fasterxml.jackson.annotation.JsonProperty

data class BasicStringResponse(
        @JsonProperty("message")
        val message: String
)