package com.music.awesomemusic.repositories

import com.music.awesomemusic.persistence.domain.SongOwner
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ISongOwnerRepository : CrudRepository<SongOwner, Long> {
    override fun findById(id: Long): Optional<SongOwner>
}