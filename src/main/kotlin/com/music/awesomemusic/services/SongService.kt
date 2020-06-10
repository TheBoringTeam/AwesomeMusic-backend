package com.music.awesomemusic.services

import com.music.awesomemusic.persistence.domain.Song
import com.music.awesomemusic.repositories.ISongRepository
import com.music.awesomemusic.utils.exceptions.basic.ResourceNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

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

    fun existsByUUID(uuid: UUID): Boolean {
        return _songRepository.existsByUuid(uuid)
    }

    fun findByUUID(uuid: UUID): Song {
        return _songRepository.findByUuid(uuid).orElseThrow { ResourceNotFoundException("Song with this uuid doesnt exist") }
    }
}