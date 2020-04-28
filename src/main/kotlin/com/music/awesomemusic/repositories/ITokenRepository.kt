package com.music.awesomemusic.repositories

import com.music.awesomemusic.domain.EmailVerificationToken
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ITokenRepository : CrudRepository<EmailVerificationToken, Long> {
    fun findByToken(token : String) : EmailVerificationToken?
}
