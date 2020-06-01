package com.music.awesomemusic.repositories

import com.music.awesomemusic.persistence.domain.Country
import org.springframework.data.repository.CrudRepository
import java.util.*

interface ICountryRepository : CrudRepository<Country, Long> {
    fun existsByCountryCode(countryCode: String): Boolean
    override fun findAll(): List<Country>
    fun findByCountryCode(countryCode: String): Optional<Country>
}