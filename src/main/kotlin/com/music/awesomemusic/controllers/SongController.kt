package com.music.awesomemusic.controllers

import com.music.awesomemusic.persistence.dto.response.BasicStringResponse
import com.music.awesomemusic.persistence.dto.response.UploadSongResponse
import com.music.awesomemusic.services.FileStorageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("api/song")
class SongController {

    @Autowired
    private lateinit var _fileStorageService: FileStorageService

    @PostMapping("upload")
    @ResponseBody
    fun uploadFile(@RequestParam("song_file") songFile: MultipartFile, @AuthenticationPrincipal userDetails: UserDetails): ResponseEntity<*> {
        // TODO: Write some db logic
        val fileName = _fileStorageService.saveFile(songFile)
        return ResponseEntity.ok(UploadSongResponse(fileName, "Song was successfully uploaded", songFile.size))
    }
}