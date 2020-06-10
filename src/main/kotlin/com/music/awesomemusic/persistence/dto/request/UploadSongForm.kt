package com.music.awesomemusic.persistence.dto.request

import com.music.awesomemusic.utils.validators.annotations.ExistsSongOwner
import javax.validation.constraints.NotEmpty

data class UploadSongForm(
        @ExistsSongOwner
        var songOwnerIdField: Long?,

        @field:NotEmpty(message = "Song name cannot be empty")
        private val songNameField: String?
) {
    val songName: String
        get() = songNameField!!
}