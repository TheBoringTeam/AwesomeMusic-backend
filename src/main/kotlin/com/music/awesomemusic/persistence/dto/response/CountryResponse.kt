package com.music.awesomemusic.persistence.dto.response

import com.fasterxml.jackson.annotation.JsonProperty

class CountryResponse(
        @JsonProperty("name")
        val countryName: String,
        @JsonProperty("code")
        val countryCode: String
)