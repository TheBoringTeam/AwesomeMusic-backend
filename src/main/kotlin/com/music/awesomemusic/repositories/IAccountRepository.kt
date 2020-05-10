package com.music.awesomemusic.repositories

import com.music.awesomemusic.persistence.domain.AwesomeAccount
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface IAccountRepository : CrudRepository<AwesomeAccount, Long> {
    fun findByUsername(username: String): Optional<AwesomeAccount>

    fun existsByEmail(email: String): Boolean

    fun existsByUsername(username: String) : Boolean
}