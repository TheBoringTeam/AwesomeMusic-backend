package com.music.awesomemusic.services

import com.music.awesomemusic.persistence.domain.AwesomeAccount
import com.music.awesomemusic.utils.exceptions.user.TooManyAttemptsException
import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.io.Serializable
import javax.servlet.http.HttpServletRequest

@Service
class AccountUserDetailsService : UserDetailsService, Serializable {
    private val _logger = Logger.getLogger(AccountUserDetailsService::class.java)

    @Autowired
    lateinit var accountService: AccountService

    @Autowired
    lateinit var loginAttemptService: LoginAttemptsService

    @Autowired
    private lateinit var request: HttpServletRequest

    override fun loadUserByUsername(username: String): UserDetails {
        if (username.isEmpty()) {
            throw UsernameNotFoundException("Username is empty")
        }

        if (loginAttemptService.isBlocked(getClientIP())) {
            throw TooManyAttemptsException("Too many unsuccessful attempts to log in. Please try later")
        }

        val user: AwesomeAccount = accountService.findByUsername(username)

        val authorities = arrayListOf<GrantedAuthority>()

//        user.roles.forEach { roleMapping ->
//            roleMapping.role.permissions.forEach { permission ->
//                authorities.add(SimpleGrantedAuthority(permission.name))
//            }
//        }

        return User(user.username, user.password, authorities)
    }

    private fun getClientIP(): String {
        val xfHeader: String = request.getHeader("X-Forwarded-For") ?: return request.remoteAddr
        return xfHeader.split(",").toTypedArray()[0]
    }
}