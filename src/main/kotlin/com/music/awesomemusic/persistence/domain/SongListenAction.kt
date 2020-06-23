package com.music.awesomemusic.persistence.domain

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "song_listen_action")
class SongListenAction(
        @ManyToOne
        @JoinColumn(name = "song_id")
        var song: Song,

        @ManyToOne
        @JoinColumn(name = "account_id")
        var account: AwesomeAccount
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "song_listen_action_id")
    val id: Long = 0

    @Column(name = "created_at", nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now()
}