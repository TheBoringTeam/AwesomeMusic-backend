package com.music.awesomemusic.services

import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.*

@Service
class FileStorageService {

    private val _logger = Logger.getLogger(FileStorageService::class.java)

    @Value("\${file.upload-dir}")
    lateinit var uploadDir: String

    fun saveFile(file: MultipartFile): String {
        val fileName = StringUtils.cleanPath(UUID.randomUUID().toString() + ".mp3")

        try {
            val targetLocation = Paths.get(uploadDir).toAbsolutePath().normalize().resolve(fileName)
            Files.copy(file.inputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING)
            return fileName
        } catch (e: Exception) {
            _logger.error("Could not save file $fileName. Error: ${e.message}")
            throw e
        }
    }
}
