package com.music.awesomemusic.services


import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletRequest


@Service
class AwesomeUserDetailsService : UserDetailsService {

    private val logger = Logger.getLogger(AwesomeUserDetailsService::class.java)

    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var loginAttemptService: LoginAttemptsService

    @Autowired
    private lateinit var request: HttpServletRequest

    override fun loadUserByUsername(username: String?): UserDetails {
        if (username.isNullOrEmpty()) {
            throw UsernameNotFoundException("Username is empty")
        }

        val ip = getClientIP();
        if (loginAttemptService.isBlocked(ip)) {
            throw RuntimeException("You’ve exceeded maximum sign in attempts. Please try again in an hour.")
        }

        val user = userService.findByUsername(username) ?: throw UsernameNotFoundException("User was not found")

        val authorities = arrayListOf<GrantedAuthority>()

        user.roles.forEach { it ->
            authorities.add(SimpleGrantedAuthority(it.roleName))
        }
        val userDetails = User(user.username, user.password, authorities)

        logger.debug("User authentication successfully: " + userDetails.password)
        return userDetails
    }

    private fun getClientIP(): String {
        val xfHeader: String = request.getHeader("X-Forwarded-For") ?: return request.remoteAddr
        return xfHeader.split(",").toTypedArray()[0]
    }
}