package com.music.awesomemusic.utils

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.core.userdetails.UserDetails
import java.util.*
import java.util.function.Function
import kotlin.collections.HashMap
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

class JwtUtil {
    private val SECRET_KEY = "AWdAWDAWDasdWAdAwDAWD"
    fun generateToken(userDetails: UserDetails): String {
        val claims = HashMap<String, JvmType.Object>()
        return createToken(claims, userDetails.username)
    }

    fun createToken(claims: Map<String, JvmType.Object>, subject: String): String {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(Date(System.currentTimeMillis())).signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact()
    }

    fun extractUsername(token: String): String {
        return extractClaim(token, Claims::getSubject as Function<Claims, String>)
    }

    fun <T> extractClaim(token: String, claimsResolver: Function<Claims, out T>): T {
        val claims = extractAllClaims(token)
        return claimsResolver.apply(claims)
    }

    fun extractExpiration(token: String): Date {
        return extractClaim(token, Claims::getExpiration as Function<Claims, Date>)
    }

    fun extractAllClaims(token: String): Claims {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).body
    }

    fun isTokenExpired(token: String): Boolean {
        return extractExpiration(token).before(Date())
    }

    fun validateToken(token: String, userDetails: UserDetails): Boolean {
        val username = extractUsername(token)
        return (username.equals(userDetails.username) && !isTokenExpired(token))
    }
}