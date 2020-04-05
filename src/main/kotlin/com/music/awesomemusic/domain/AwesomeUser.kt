package com.music.awesomemusic.models


import com.music.awesomemusic.domain.Role
import javax.persistence.*

@Entity
@Table(name = "awesome_user")
class AwesomeUser(username: String, @Column(name = "password", nullable = false) var password: String) {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    val id: Long = 0

    @Column(name = "username", nullable = false)
    var username: String = username
        private set

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
            inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")])
    var roles: List<Role> = arrayListOf()

}