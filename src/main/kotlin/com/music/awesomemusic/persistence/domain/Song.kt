package com.music.awesomemusic.persistence.domain

import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "song")
data class Song(
        @Column(name = "song_name")
        var name: String,

        @Column(name = "song_uuid")
        var uuid: UUID,

        @Column(name = "length")
        var length: Int,

        @ManyToOne
        @JoinColumn(name = "song_owner_id")
        var owner: SongOwner,

        @Column(name = "song_image")
        var image: String
) {
    @Id
    @Column(name = "song_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0

    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now()

    @Column(name = "disabled_at")
    var disabledAt: LocalDateTime? = null

    @Column(name = "song_listen_count")
    var counter: Long = 0
}