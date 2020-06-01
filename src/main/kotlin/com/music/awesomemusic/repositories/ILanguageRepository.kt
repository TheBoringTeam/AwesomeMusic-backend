package com.music.awesomemusic.repositories

import com.music.awesomemusic.persistence.domain.Language
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ILanguageRepository : CrudRepository<Language, Long> {
    fun existsByLanguageCode(languageCode: String): Boolean
    override fun findAll(): List<Language>
    fun findByLanguageCode(languageCode: String): Optional<Language>
}