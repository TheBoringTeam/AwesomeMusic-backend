package com.music.awesomemusic.persistence.domain

import javax.persistence.*

@Entity
@Table(name = "tag")
class Tag (
        @Column(name = "tag_name", nullable = false, unique = true)
        var name: Int,

        @ManyToOne
        @JoinColumn(name = "tag_group_id")
        var group: TagGroup
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tag_id")
    val id: Long = 0
}