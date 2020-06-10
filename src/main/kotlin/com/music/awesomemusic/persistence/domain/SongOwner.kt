package com.music.awesomemusic.persistence.domain

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "song_owner")
class SongOwner(
        @Column(name = "song_owner_name", length = 64, nullable = false)
        var name: String,

        @Column(name = "song_owner_email", length = 254, nullable = false)
        var email: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "song_owner_id")
    val id: Long = 0

    @Column(name = "comment", length = 1024, nullable = true)
    var biography: String = ""

    @Column(name = "created_at", nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now()
}