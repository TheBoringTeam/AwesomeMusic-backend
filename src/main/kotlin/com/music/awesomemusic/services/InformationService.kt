package com.music.awesomemusic.services

import com.music.awesomemusic.repositories.ICountryRepository
import com.music.awesomemusic.repositories.ILanguageRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class InformationService {
    @Autowired
    lateinit var languageRepository: ILanguageRepository

    @Autowired
    lateinit var countryRepository: ICountryRepository

    fun getLanguageCodes(): List<String> {
        return languageRepository.findAll().map { it.languageCode }
    }

    fun languageExistsByCode(languageCode: String): Boolean {
        return languageRepository.existsByLanguageCode(languageCode)
    }

    fun countryExistsByCode(countryCode: String): Boolean {
        return countryRepository.existsByCountryCode(countryCode)
    }
}