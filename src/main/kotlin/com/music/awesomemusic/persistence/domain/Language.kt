package com.music.awesomemusic.persistence.domain

import javax.persistence.*

@Entity
class Language(
        @Column(name = "language_code", unique = true, nullable = false)
        val languageCode: String,

        @Column(name = "language_name", unique = true, nullable = false)
        val languageName: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "language_id")
    val id: Long = 0
}