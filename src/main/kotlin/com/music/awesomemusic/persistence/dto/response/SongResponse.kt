package com.music.awesomemusic.persistence.dto.response

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class SongResponse @JsonCreator constructor(
        @JsonProperty("song_name")
        var songName: String
)