package com.music.awesomemusic.persistence.domain

import javax.persistence.*

@Entity
@Table(name = "awesome_role")
class Role(roleName: String) {
    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0

    @Column(name = "role_name")
    var roleName: String = roleName
        private set
}