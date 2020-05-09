package com.music.awesomemusic.persistence.domain

import java.net.URL
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*
import kotlin.collections.ArrayList


@Entity
@Table(name = "awesome_account")
class AwesomeAccount(
        @Column(name = "account_dog", length = 16, unique = true, nullable = false)
        var username: String,

        @Column(name = "password", length = 32, nullable = false)
        var password: String,

        @Column(name = "account_email", length = 254, unique = true, nullable = false)
        var email: String,

        @Column(name = "public_name", nullable = false)
        var name: String,

        @Column(name = "is_musician_collective", nullable = false)
        var isCollective: Boolean = false
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "account_id")
    val id: Long = 0

    @Column(name = "musician_biography", length = 1024)
    var biography: String = ""

    @Column(name = "birthday", nullable = true)
    var birthday: Date? = null

    @Column(name = "deathday", nullable = true)
    var deathDay: Date? = null

    @Column(name = "gender", nullable = true)
    var gender: Gender? = null

    @Column(name = "education", nullable = true)
    var education: Education? = null

    @ManyToOne
    @JoinColumn(name = "country_id", nullable = true)
    var country: Country? = null

    @ManyToOne
    @JoinColumn(name = "language_id", nullable = true)
    var language: Language? = null

    @Column(name = "account_image", nullable = true)
    var imageLink: URL? = null

    @Column(name = "follower_count", nullable = false)
    var followerCounter: Long = 0

    @Column(name = "is_activated", nullable = false)
    var isActivated: Boolean = false

    @Column(name = "is_verified", nullable = false)
    var isVerified: Boolean = false

    @Column(name = "is_blocked", nullable = false)
    var isBlocked: Boolean = false

    @Column(name = "created_at", nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now()

//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "account_role", joinColumns = [JoinColumn(name = "account_id", referencedColumnName = "account_id")],
//            inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "role_id")])
//    var roles: List<Role> = arrayListOf()

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY, orphanRemoval = true)
    var accountRoles: List<AccountRole> = arrayListOf()

    fun addRole(role: Role, expiry_at: LocalDateTime? = null) {
        val accountRole = AccountRole(this, role, expiry_at)
        (accountRoles as ArrayList).add(accountRole)
    }
}