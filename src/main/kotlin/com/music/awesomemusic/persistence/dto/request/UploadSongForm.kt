package com.music.awesomemusic.persistence.dto.request

import com.fasterxml.jackson.annotation.JsonProperty
import com.music.awesomemusic.utils.validators.annotations.ExistsSongOwner
import javax.validation.constraints.NotEmpty

data class UploadSongForm(
        @JsonProperty("song_owner_id")
        @ExistsSongOwner
        var songOwnerIdField: Long?,

        @JsonProperty("song_name")
        @field:NotEmpty(message = "Song name cannot be empty")
        private val songNameField: String?
) {
    val songName: String
        get() = songNameField!!
}