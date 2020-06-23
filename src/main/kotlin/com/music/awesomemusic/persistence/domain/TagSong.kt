package com.music.awesomemusic.persistence.domain

import java.time.LocalDateTime
import javax.persistence.*


@Entity
@Table(name = "tag_song")
class TagSong (
        @ManyToOne
        @JoinColumn(name = "song_id")
        var song: Song,

        @ManyToOne
        @JoinColumn(name = "tag_id")
        var tag: Tag
){
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tag_song_id")
    val id: Long = 0

    @Column(name = "tag_song_rating", nullable = false)
    var songRating: Int = 0

    @Column(name = "created_at", nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now()
}