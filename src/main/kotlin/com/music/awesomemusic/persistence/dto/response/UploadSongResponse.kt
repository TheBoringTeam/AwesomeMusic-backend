package com.music.awesomemusic.persistence.dto.response

import com.fasterxml.jackson.annotation.JsonProperty

data class UploadSongResponse(
        @JsonProperty("fileName")
        val fileName: String,

        @JsonProperty("message")
        val message: String,

        @JsonProperty("size")
        val size: Long
)