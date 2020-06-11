package com.music.awesomemusic.services

import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.*

@Service
class FileStorageService {

    private val _logger = Logger.getLogger(FileStorageService::class.java)

    @Value("\${file.song.upload-dir}")
    lateinit var songUploadDir: String

    @Value("\${file.image.upload-dir}")
    lateinit var imageUploadDir: String

    fun saveSong(songFile: MultipartFile, imageFile: MultipartFile, uuid: UUID): String {
        //Clean path names
        val songFileName = StringUtils.cleanPath("$uuid.mp3")
        val imageFileName = StringUtils.cleanPath("$uuid.jpg")

        try {
            // Prepare space for new files
            val songTargetLocation = Paths.get(songUploadDir).toAbsolutePath().normalize().resolve(songFileName)
            val imageTargetLocation = Paths.get(imageUploadDir).toAbsolutePath().normalize().resolve(imageFileName)

            // Save files to local storage
            Files.copy(songFile.inputStream, songTargetLocation, StandardCopyOption.REPLACE_EXISTING)
            Files.copy(imageFile.inputStream, imageTargetLocation, StandardCopyOption.REPLACE_EXISTING)

            return songFileName
        } catch (e: Exception) {
            _logger.error("Could not save file $songFileName. Error: ${e.message}")
            throw e
        }
    }

    fun getSongSize(fileName: String): Long {
        return sizeFromFile(Paths.get(songUploadDir + fileName))
    }

    private fun sizeFromFile(filePath: Path): Long {
        try {
            return Files.size(filePath)
        } catch (e: Exception) {
            _logger.error("Error during extracting file size: ${e.message}")
            throw e
        }
    }
}
