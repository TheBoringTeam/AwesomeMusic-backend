package com.music.awesomemusic.controllers

import com.music.awesomemusic.persistence.domain.Country
import com.music.awesomemusic.persistence.domain.Language
import com.music.awesomemusic.persistence.dto.response.CountryResponse
import com.music.awesomemusic.persistence.dto.response.LanguageResponse
import com.music.awesomemusic.services.InformationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/info")
class InformationController {

    @Autowired
    lateinit var informationService: InformationService

    @GetMapping("/languages")
    fun getAllLanguage(): ResponseEntity<List<LanguageResponse>> {
        return ResponseEntity.ok(informationService.getAllLanguages().map {
            LanguageResponse(it.languageName, it.languageCode)
        })
    }

    @GetMapping("/countries")
    fun getAllCountries(): ResponseEntity<List<CountryResponse>> {
        return ResponseEntity.ok(informationService.getAllCountries().map {
            CountryResponse(it.countryName, it.countryCode)
        })
    }
}