package com.music.awesomemusic.persistence.domain

import javax.persistence.*


@Entity
@Table(name = "action")
class Action (
        @Column(name = "action_name", nullable = false, length = 16, unique = true)
        var name: String
){
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "action_id")
    val id: Long = 0

    @Enumerated(EnumType.STRING)
    var objectType: ObjectType? = null
}