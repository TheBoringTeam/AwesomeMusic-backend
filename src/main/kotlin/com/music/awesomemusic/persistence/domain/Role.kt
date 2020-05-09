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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role_permission", joinColumns = [JoinColumn(name = "role_id", referencedColumnName = "role_id")],
            inverseJoinColumns = [JoinColumn(name = "permission_id", referencedColumnName = "permission_id")])
    var permissions: List<Permission> = arrayListOf()
}