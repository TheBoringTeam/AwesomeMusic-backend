package com.music.awesomemusic.repositories

import com.music.awesomemusic.persistence.domain.Song
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ISongRepository : CrudRepository<Song, Long> {
    override fun findById(id: Long): Optional<Song>

    fun existsByName(name: String): Boolean

    override fun existsById(id: Long): Boolean
}