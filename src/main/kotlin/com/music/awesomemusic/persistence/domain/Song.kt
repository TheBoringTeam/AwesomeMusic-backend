package com.music.awesomemusic.persistence.domain

import java.net.URL
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "song")
class Song (
        @Column(name = "song_name", nullable = false, length = 64)
        var name: String,

        @Column(name = "song_link", nullable = true)
        var songLink: URL,

        @Column(name = "song_length", nullable = true)
        val inMinutes: Double,

        @ManyToOne
        @JoinColumn(name = "song_owner_id", nullable = true)
        var owner: SongOwner
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "song_id")
    val id: Long = 0

    @Column(name = "song_image", nullable = true)
    var imageLink: URL? = null

    @Column(name = "song_listen_count", nullable = false)
    var listenCount: Long = 0

    @Column(name = "created_at", nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now()

    @Column(name = "disabled_at", nullable = true)
    var disabledAt: LocalDateTime = LocalDateTime.now()
}