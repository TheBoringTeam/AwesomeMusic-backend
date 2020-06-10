package com.music.awesomemusic.persistence.domain

import java.net.URL
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "account_action")
class Playlist (
        @ManyToOne
        @JoinColumn(name = "account_id")
        var account: AwesomeAccount,

        @Column(name = "playlist_name", length = 64)
        var name: String
){
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "playlist_id")
    val id: Long = 0

    @Column(name = "playlist_image", nullable = true, length = 64)
    var imageLink: URL? = null

    @Column(name = "is_public", nullable = false)
    var isPublic: Boolean = true

    @Column(name = "created_at", nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now()
}