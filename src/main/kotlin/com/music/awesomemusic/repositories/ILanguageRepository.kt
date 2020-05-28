package com.music.awesomemusic.repositories

import com.music.awesomemusic.persistence.domain.Language
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ILanguageRepository : CrudRepository<Language, Long> {
    fun existsByLanguageCode(languageCode: String): Boolean
}