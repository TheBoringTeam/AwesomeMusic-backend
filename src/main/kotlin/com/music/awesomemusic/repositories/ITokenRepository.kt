package com.music.awesomemusic.repositories

import com.music.awesomemusic.persistence.domain.TokenType
import com.music.awesomemusic.persistence.domain.VerificationToken
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ITokenRepository : CrudRepository<VerificationToken, Long> {
    fun findByTokenAndTokenType(token : String, tokenType: TokenType) : Optional<VerificationToken>
}
