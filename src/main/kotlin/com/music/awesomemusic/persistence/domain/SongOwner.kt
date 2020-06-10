package com.music.awesomemusic.persistence.domain

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "song_owner")
data class SongOwner(
        @Column(name = "song_owner_name")
        var name: String,

        @Column(name = "song_owner_email")
        var email: String,

        @Column(name = "comment")
        var comment: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "song_owner_id")
    val id: Long = 0

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    val createdAt: LocalDateTime = LocalDateTime.now()
}