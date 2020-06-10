package com.music.awesomemusic.persistence.domain

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "tag_song_rate")
class TagSongRate (
        @ManyToOne
        @JoinColumn(name = "tag_song_id")
        var tagSong: TagSong,

        @ManyToOne
        @JoinColumn(name = "account_id")
        var account: AwesomeAccount
) {
    @Column(name = "is_positive_rate", nullable = false)
    var isPositive: Boolean = true

    @Column(name = "created_at", nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now()
}