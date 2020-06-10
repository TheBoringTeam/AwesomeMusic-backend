package com.music.awesomemusic.persistence.domain

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "tag_account")
class TagAccount(
        @ManyToOne
        @JoinColumn(name = "account_id")
        var account: AwesomeAccount,

        @ManyToOne
        @JoinColumn(name = "tag_id")
        var tag: Tag
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tag_account_id")
    val id: Long = 0

    @Column(name = "account_tag_rating", nullable = false)
    var accountRating: Int = 0

    @Column(name = "created_at", nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now()
}