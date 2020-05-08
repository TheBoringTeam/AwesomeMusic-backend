package com.music.awesomemusic.domain.persistence

import java.time.LocalDateTime
import javax.persistence.*


@Entity
@Table(name = "account_role")
class AccountRole(
        @Column(name = "expiry_at", nullable = true)
        var expiryAt: LocalDateTime? = null,

        @ManyToOne
        @JoinColumn(name = "account_id")
        var account: AwesomeAccount,

        @ManyToOne
        @JoinColumn(name = "role_id")
        var role: Role
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "account_role_id")
    val id: Long = 0

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
}