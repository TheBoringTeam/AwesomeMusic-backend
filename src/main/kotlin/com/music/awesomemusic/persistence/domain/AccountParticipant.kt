package com.music.awesomemusic.persistence.domain

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "account_participant")
class AccountParticipant(
        @ManyToOne
        @JoinColumn(name = "account_id")
        var account: AwesomeAccount,

        @ManyToOne
        @JoinColumn(name = "account_role_id")
        var role: AccountRole
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "account_participant_id")
    val id: Long = 0

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
}