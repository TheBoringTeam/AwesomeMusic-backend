package com.music.awesomemusic.models


import javax.persistence.*

@Entity
@Table(name = "tusers")
class AwesomeUser(username: String, password: String) {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "userid")
    val id: Long = 0

    @Column(name = "username", nullable = false)
    var username: String = username
        private set

    @Column(name = "userpass", nullable = false)
    var password: String = password
        private set

    @Column(name = "comment")
    var comment: String = ""
        private set
}