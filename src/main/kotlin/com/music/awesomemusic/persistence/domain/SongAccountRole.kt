package com.music.awesomemusic.persistence.domain

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "song_account_role")
class SongAccountRole(
        @ManyToOne
        @JoinColumn(name = "song_id")
        var song: Song,

        @ManyToOne
        @JoinColumn(name = "account_role_id")
        var accountRole: AccountRole
) {
    @Column(name = "created_at", nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now()
}