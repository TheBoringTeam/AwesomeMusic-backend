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
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tag_song_rate_id")
    val id: Long = 0

    @Column(name = "is_positive_rate", nullable = false)
    var isPositive: Boolean = true

    @Column(name = "created_at", nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now()
}