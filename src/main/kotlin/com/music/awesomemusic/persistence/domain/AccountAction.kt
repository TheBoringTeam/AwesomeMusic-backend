package com.music.awesomemusic.persistence.domain

import java.net.InetAddress
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "account_action")
class AccountAction(
        @ManyToOne
        @JoinColumn(name = "account_id")
        var account: AwesomeAccount,

        @ManyToOne
        @JoinColumn(name = "action_id")
        var action: Action,

        @Column(name = "account_IP")
        var accountIP: InetAddress
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "account_action_id")
    val id: Long = 0

    @Column(name = "action_info", length = 256, nullable = true)
    var actionInfo: String = ""

    // object id?
    @Column(name = "object_id", nullable = true)
    var objectId: Long = 0

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
}