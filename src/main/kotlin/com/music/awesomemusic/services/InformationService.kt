package com.music.awesomemusic.services

import com.music.awesomemusic.repositories.ILanguageRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service

@Service
class InformationService {
    @Autowired
    lateinit var languageRepository: ILanguageRepository

    fun getAllLanguageCodes() {
        
    }
}