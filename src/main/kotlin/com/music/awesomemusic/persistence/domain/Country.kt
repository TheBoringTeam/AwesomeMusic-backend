package com.music.awesomemusic.persistence.domain

import javax.persistence.*

@Entity
@Table(name = "country")
class Country(
        @Column(name = "country_code", unique = true, nullable = false)
        val countryCode: String,

        @Column(name = "country_name", unique = true, nullable = false)
        val countryName: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "country_id")
    val id: Long = 0
}