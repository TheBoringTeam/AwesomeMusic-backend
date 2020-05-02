package com.music.awesomemusic.domain

import com.music.awesomemusic.domain.AwesomeUser
import javax.persistence.*

@Entity
@Table(name = "awesome_role")
class Role(roleName: String, desc: String) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0

    @Column(name = "role_name")
    var roleName: String = roleName
        private set

    @Column(name = "description")
    var description: String = desc

    // possible inverse join, but wdf we need this ?
//    @ManyToMany(mappedBy = "roles")
//    var employees:Set<AwesomeUser> = hashSetOf()
}