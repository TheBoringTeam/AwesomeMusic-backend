package com.music.awesomemusic.repositories

import com.music.awesomemusic.persistence.domain.EmailVerificationToken
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface IEmailTokenRepository : CrudRepository<EmailVerificationToken, Long> {
    fun findByToken(token : String) : EmailVerificationToken?
}
