package com.music.awesomemusic.persistence.domain

import javax.persistence.*

@Entity
@Table(name = "permission")
class Permission(
        @Column(name = "permission_name", nullable = false, unique = true)
        val name: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "permission_id")
    val id: Long = 0
}