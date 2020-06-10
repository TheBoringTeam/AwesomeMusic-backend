package com.music.awesomemusic.services

import com.music.awesomemusic.persistence.domain.Song
import com.music.awesomemusic.repositories.ISongRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SongService {
    @Autowired
    private lateinit var _songRepository: ISongRepository

    fun existsByName(songName: String): Boolean {
        return _songRepository.existsByName(songName)
    }

    fun save(song: Song): Song {
        return _songRepository.save(song)
    }
}