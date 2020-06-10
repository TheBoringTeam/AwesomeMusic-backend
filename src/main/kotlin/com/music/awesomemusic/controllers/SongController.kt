package com.music.awesomemusic.controllers

import com.music.awesomemusic.persistence.dto.request.UploadSongForm
import com.music.awesomemusic.persistence.dto.response.UploadSongResponse
import com.music.awesomemusic.services.FileStorageService
import com.music.awesomemusic.services.SongService
import com.music.awesomemusic.utils.exceptions.basic.WrongArgumentsException
import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("api/song")
class SongController {

    @Autowired
    private lateinit var _fileStorageService: FileStorageService

    @Autowired
    private lateinit var _songService: SongService

    private val _logger = Logger.getLogger(SongController::class.java)

    @PostMapping("upload")
    @ResponseBody
    fun uploadFile(@RequestParam("song_file") songFile: MultipartFile, @RequestBody(required = false) uploadSongForm: UploadSongForm,
                   @AuthenticationPrincipal userDetails: UserDetails, bindingResult: BindingResult): ResponseEntity<*> {
        //form validation
        if (bindingResult.hasErrors()) {
            throw WrongArgumentsException(bindingResult.allErrors[0].defaultMessage)
        }


        _logger.info("Start uploading file with name [${songFile.originalFilename}]...")
        val fileName = _fileStorageService.saveFile(songFile)
        _logger.info("Song with name [$fileName] was saved")


        return ResponseEntity.ok(UploadSongResponse(fileName, "Song was successfully uploaded", songFile.size))
    }
}