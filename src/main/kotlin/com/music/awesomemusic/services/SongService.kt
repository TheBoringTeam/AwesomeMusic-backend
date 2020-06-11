package com.music.awesomemusic.services

import com.music.awesomemusic.persistence.domain.Song
import com.music.awesomemusic.repositories.ISongRepository
import com.music.awesomemusic.utils.exceptions.basic.ResourceNotFoundException
import com.music.awesomemusic.utils.validators.annotations.ExistsSongByUUID
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class SongService {
    @Autowired
    private lateinit var _songRepository: ISongRepository

    @Autowired
    private lateinit var _fileStorageService: FileStorageService

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

    fun prepareSong(songUUID: String, range: String) {
        val rangeStart = 0L
        val rangeEnd = 0L
        var data = byteArrayOf()
        val fileSize = 0L

        val fileName = "$songUUID.mp3"

        try {
            if (range.isEmpty()) {
                // TODO : Return full file size
            }

        } catch (e: Exception) {

        }

    }
}