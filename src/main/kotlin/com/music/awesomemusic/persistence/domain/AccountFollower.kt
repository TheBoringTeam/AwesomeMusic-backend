package com.music.awesomemusic.persistence.domain

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "account_follower")
class AccountFollower (
        @ManyToOne
        @JoinColumn(name = "account_id_from")
        val accountFrom: AwesomeAccount,

        @ManyToOne
        @JoinColumn(name = "account_id_to")
        val AccountTo: AwesomeAccount
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "account_follower_id")
    val id: Long = 0

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
}