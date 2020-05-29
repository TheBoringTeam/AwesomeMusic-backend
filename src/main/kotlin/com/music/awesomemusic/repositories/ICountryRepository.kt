package com.music.awesomemusic.repositories

import com.music.awesomemusic.persistence.domain.Country
import org.springframework.data.repository.CrudRepository

interface ICountryRepository : CrudRepository<Country, Long> {
    fun existsByCountryCode(countryCode: String): Boolean
}