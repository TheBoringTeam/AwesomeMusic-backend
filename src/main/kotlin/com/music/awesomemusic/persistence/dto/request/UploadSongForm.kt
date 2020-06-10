package com.music.awesomemusic.persistence.dto.request

import com.music.awesomemusic.utils.validators.annotations.ExistsSongOwner
import javax.validation.constraints.NotEmpty

data class UploadSongForm(
        @ExistsSongOwner
        var songOwnerId: Long?
)