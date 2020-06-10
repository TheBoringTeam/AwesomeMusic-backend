package com.music.awesomemusic.services

import com.music.awesomemusic.persistence.domain.Country
import com.music.awesomemusic.persistence.domain.Language
import com.music.awesomemusic.repositories.ICountryRepository
import com.music.awesomemusic.repositories.ILanguageRepository
import com.music.awesomemusic.repositories.ISongOwnerRepository
import com.music.awesomemusic.utils.exceptions.basic.ResourceNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class InformationService {
    @Autowired
    private lateinit var languageRepository: ILanguageRepository

    @Autowired
    private lateinit var countryRepository: ICountryRepository

    @Autowired
    private lateinit var _songOwnerRepository: ISongOwnerRepository

    fun findLanguageCodes(): List<String> {
        return languageRepository.findAll().map { it.languageCode }
    }

    fun languageExistsByCode(languageCode: String): Boolean {
        return languageRepository.existsByLanguageCode(languageCode)
    }

    fun countryExistsByCode(countryCode: String): Boolean {
        return countryRepository.existsByCountryCode(countryCode)
    }

    fun findAllLanguages(): List<Language> {
        return languageRepository.findAll()
    }

    fun findAllCountries(): List<Country> {
        return countryRepository.findAll()
    }

    fun findCountryByCode(code: String): Country {
        return countryRepository.findByCountryCode(code).orElseThrow { ResourceNotFoundException("There is no country with this code") }
    }

    fun findLanguageByCode(code: String): Language {
        return languageRepository.findByLanguageCode(code).orElseThrow { ResourceNotFoundException("There is no language with this code") }
    }

    fun existsSongOwnerById(songOwnerId: Long): Boolean {
        return _songOwnerRepository.existsById(songOwnerId)
    }
}