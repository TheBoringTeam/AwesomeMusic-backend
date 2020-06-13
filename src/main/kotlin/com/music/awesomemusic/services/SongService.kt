package com.music.awesomemusic.services

import com.music.awesomemusic.persistence.domain.Song
import com.music.awesomemusic.repositories.ISongRepository
import com.music.awesomemusic.utils.exceptions.basic.ResourceNotFoundException
import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.util.*

@Service
class SongService {

    private val _logger = Logger.getLogger(SongService::class.java)

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

    fun prepareSong(songUUID: String, range: String?): ResponseEntity<ByteArray> {
        var rangeStart = 0L
        var rangeEnd = 0L
        var data = byteArrayOf()
        var fileSize = 0L

        val fileName = "$songUUID.mp3"

        try {
            fileSize = _fileStorageService.getSongSize(fileName)
            if (range == null) {
                return ResponseEntity.status(HttpStatus.OK)
                        .header("Content-Type", "audio/mpeg")
                        .header("Content-Length", fileSize.toString())
                        .body(_fileStorageService.readByteRange(fileName, rangeStart, fileSize - 1))
            }

            val ranges = range.split("-")
            rangeStart = ranges[0].substring(6).toLong()

            rangeEnd = if (ranges.size > 1) {
                range[1].toLong()
            } else {
                fileSize - 1
            }

            if (fileSize < rangeEnd) {
                rangeEnd = fileSize - 1
            }

            data = _fileStorageService.readByteRange(fileName, rangeStart, rangeEnd)
        } catch (e: Exception) {
            _logger.error("Error during preparing a song for streaming: ${e.message}")
            throw e
        }
        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .header("Content-Type", "audio/mpeg")
                .header("Accept-Ranges", "1024")
                .header("Content-Length", ((rangeEnd - rangeStart) + 1).toString())
                .header("Content-Range", "1024 $rangeStart-$rangeEnd/$fileSize")
                .body(data)
    }
}