package com.music.awesomemusic.services

import com.music.awesomemusic.persistence.domain.AwesomeAccount
import com.music.awesomemusic.persistence.domain.TokenType
import com.music.awesomemusic.persistence.domain.VerificationToken
import com.music.awesomemusic.repositories.ITokenRepository
import com.music.awesomemusic.utils.exceptions.basic.ResourceNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class TokenService {

    @Autowired
    lateinit var tokenRepository: ITokenRepository

    fun isTokenExpired(verificationToken: VerificationToken): Boolean {
        val cal = Calendar.getInstance()
        return verificationToken.expiryDate.time - cal.time.time <= 0
    }

    fun getEmailVerificationToken(token: String): VerificationToken {
        return tokenRepository.findByTokenAndTokenType(token, TokenType.REGISTRATION_EMAIL).orElseThrow { ResourceNotFoundException("Email token was not found") }
    }

    fun getResetPasswordToken(token: String): VerificationToken {
        return tokenRepository.findByTokenAndTokenType(token, TokenType.PASSWORD_RESET).orElseThrow { ResourceNotFoundException("Password reset token was not found") }
    }

    fun createEmailVerificationToken(account: AwesomeAccount, token: String) {
        val emailVerificationToken = VerificationToken(token, account, TokenType.REGISTRATION_EMAIL)
        tokenRepository.save(emailVerificationToken)
    }

    fun createPasswordResetToken(account: AwesomeAccount, token: String) {
        val passwordResetToken = VerificationToken(token, account, TokenType.PASSWORD_RESET)
        tokenRepository.save(passwordResetToken)
    }
}