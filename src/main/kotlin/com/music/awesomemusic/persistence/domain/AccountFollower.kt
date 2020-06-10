package com.music.awesomemusic.persistence.domain

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "account_follower")
class AccountFollower (
        @ManyToOne
        @JoinColumn(name = "account_id")
        var account: AwesomeAccount,

        @ManyToOne
        @JoinColumn(name = "account_id")
        var followerAccount: AwesomeAccount //?
) {
    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
}