package com.music.awesomemusic.persistence.domain

import javax.persistence.*

@Entity
@Table(name = "tag_group")
class TagGroup (
        @Column(name = "tag_group_name", nullable = false, unique = true)
        var name: Int
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tag_group_id")
    val id: Long = 0
}