package com.music.awesomemusic.models


import com.music.awesomemusic.domain.Role
import org.hibernate.annotations.CollectionId
import javax.persistence.*

@Entity
@Table(name = "awesome_user")
class AwesomeUser(
        username: String,

        @Column(name = "password", nullable = false)
        var password: String,

        @Column(name = "email")
        val email: String
) {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    val id: Long = 0

    @Column(name = "username", nullable = false)
    var username: String = username
        private set

    @Column(name = "is_activated")
    var isActivated: Boolean = false

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
            inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")])
    var roles: List<Role> = arrayListOf()

}