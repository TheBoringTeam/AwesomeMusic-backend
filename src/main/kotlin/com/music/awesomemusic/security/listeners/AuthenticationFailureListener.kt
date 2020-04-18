package com.music.awesomemusic.security.listeners

import com.music.awesomemusic.services.LoginAttemptsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationListener
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent
import org.springframework.security.web.authentication.WebAuthenticationDetails
import org.springframework.stereotype.Component

@Component
class AuthenticationFailureListener : ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    @Autowired
    private lateinit var loginAttemptsService: LoginAttemptsService

    override fun onApplicationEvent(event: AuthenticationFailureBadCredentialsEvent) {
        val auth = event.authentication.details as WebAuthenticationDetails

        loginAttemptsService.loginFailed(auth.remoteAddress)
    }

}