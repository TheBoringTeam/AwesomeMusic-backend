package com.music.awesomemusic.persistence.domain

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "playlist_song")
class PlaylistSong (
        @ManyToOne
        @JoinColumn(name = "playlist_id")
        var playlist: Playlist,

        @ManyToOne
        @JoinColumn(name = "song_id")
        var song: Song
) {
    @Column(name = "created_at", nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now()
}