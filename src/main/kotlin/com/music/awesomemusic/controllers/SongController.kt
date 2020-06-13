package com.music.awesomemusic.controllers

import com.music.awesomemusic.persistence.domain.Song
import com.music.awesomemusic.persistence.dto.response.UploadSongResponse
import com.music.awesomemusic.services.FileStorageService
import com.music.awesomemusic.services.InformationService
import com.music.awesomemusic.services.SongService
import com.music.awesomemusic.utils.validators.annotations.ExistsSongByUUID
import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.*

@RestController
@RequestMapping("api/song")
class SongController {

    @Autowired
    private lateinit var _fileStorageService: FileStorageService

    @Autowired
    private lateinit var _songService: SongService

    @Autowired
    private lateinit var _informationService: InformationService

    private val _logger = Logger.getLogger(SongController::class.java)

    @PostMapping("/upload")
    fun uploadFile(@RequestParam("song_file", required = true) songFile: MultipartFile, @RequestParam("song_name") songName: String,
                   @RequestParam("song_image_file", required = true) songImageFile: MultipartFile, @AuthenticationPrincipal userDetails: UserDetails): ResponseEntity<*> {

        // attach to song "Unknown" song owner as default
        val songOwner = _informationService.findSongOwnerById(1)

        // TODO: Extract song length
        val song = Song(songName, UUID.randomUUID(), 0, songOwner)

        // Save song to local storage
        _logger.info("Start uploading file with name [${songFile.originalFilename}]...")
        val fileName = _fileStorageService.saveSong(songFile, songImageFile, song.uuid)
        _logger.info("Song with name [$fileName] was saved")

        // Save song to database
        _songService.save(song)
        return ResponseEntity.ok(UploadSongResponse(fileName, "Song was successfully uploaded", songFile.size))
    }

    @GetMapping("/{songUUID}")
    fun getSong(@PathVariable("songUUID", required = true) @ExistsSongByUUID songUUID: UUID, @AuthenticationPrincipal userDetails: UserDetails,
                @RequestHeader(value = "Range", required = false) rangeList: String?): ResponseEntity<*> {
        return _songService.prepareSong(songUUID.toString(), rangeList)
    }
}