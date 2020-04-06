package com.music.awesomemusic.security

import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import java.util.*
import javax.annotation.PostConstruct
import javax.servlet.http.HttpServletRequest


@Component
class JwtTokenProvider {
    @Value("\${security.jwt.token.secret-key:secret}")
    private var secretKey = "secret"

    @Value("\${security.jwt.token.expire-length:3600000}")
    private val validityInMilliseconds: Long = 3600000 // 1h    @Autowired
    private val userDetailsService: UserDetailsService? = null

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
        val userDetails = userDetailsService!!.loadUserByUsername(getUsername(token))
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    fun getUsername(token: String?): String {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).body.subject
    }

    fun resolveToken(req: HttpServletRequest): String? {
        val bearerToken = req.getHeader("Authorization")
        return if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            bearerToken.substring(7, bearerToken.length)
        } else null
    }

    fun validateToken(token: String?): Boolean {
        return try {
            val claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
            !claims.body.expiration.before(Date())
        } catch (e: JwtException) {
            throw Exception("Expired or invalid JWT token")
        } catch (e: IllegalArgumentException) {
            throw Exception("Expired or invalid JWT token")
        }
    }
}