package com.music.awesomemusic.persistence.domain

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "tag_account_rate")
class TagAccountRate (
        @ManyToOne
        @JoinColumn(name = "tag_account_id")
        var tagAccount: TagAccount,

        @ManyToOne
        @JoinColumn(name = "account_id")
        var account: AwesomeAccount
) {
    @Column(name = "is_positive_rate", nullable = false)
    var isPositive: Boolean = true

    @Column(name = "created_at", nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now()
}