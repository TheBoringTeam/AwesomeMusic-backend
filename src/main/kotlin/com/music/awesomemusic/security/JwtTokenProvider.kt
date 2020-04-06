package com.music.awesomemusic.security

import com.music.awesomemusic.controllers.UserController
import com.music.awesomemusic.services.AwesomeUserDetailsService
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.util.*
import javax.annotation.PostConstruct
import javax.servlet.http.HttpServletRequest


@Component
class JwtTokenProvider {

    private val logger = Logger.getLogger(JwtTokenProvider::class.java)

    @Value("\${security.jwt.token.secret-key}")
    private lateinit var secretKey: String

    @Value("\${security.jwt.token.expire-length:}")
    private var validityInMilliseconds: Long = 3600000 * 24 * 7 // 3600000 - 1h

    @Autowired
    private lateinit var userDetailsService: AwesomeUserDetailsService

    @PostConstruct
    protected fun init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.toByteArray())
    }

    fun createToken(username: String?, roles: List<String?>): String {
        val claims = Jwts.claims().setSubject(username)
        claims["roles"] = roles
        val now = Date()
        val validity = Date(now.getTime() + validityInMilliseconds)
        return Jwts.builder() //
                .setClaims(claims) //
                .setIssuedAt(now) //
                .setExpiration(validity) //
                .signWith(SignatureAlgorithm.HS256, secretKey) //
                .compact()
    }

    fun getAuthentication(token: String?): Authentication {
        val userDetails = userDetailsService.loadUserByUsername(getUsername(token))
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    fun getUsername(token: String?): String {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).body.subject
    }

    fun resolveToken(req: HttpServletRequest): String? {
        val awesomeToken = req.getHeader("Authorization")
        return if (awesomeToken != null && awesomeToken.startsWith("AwesomeToken ")) {
            awesomeToken.substring(13, awesomeToken.length)
        } else null
    }

    fun validateToken(token: String): Boolean {
        try {
            val claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)

            if (claims.body.expiration.before(Date()))
                return false

            return true
        } catch (e: Exception) {
            throw e
        }
    }
}