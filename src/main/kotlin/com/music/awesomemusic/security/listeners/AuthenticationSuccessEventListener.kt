package com.music.awesomemusic.security.listeners

import com.music.awesomemusic.services.LoginAttemptsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationListener
import org.springframework.security.authentication.event.AuthenticationSuccessEvent
import org.springframework.security.web.authentication.WebAuthenticationDetails
import org.springframework.stereotype.Component

@Component
class AuthenticationSuccessEventListener : ApplicationListener<AuthenticationSuccessEvent> {

    @Autowired
    private lateinit var loginAttemptsService: LoginAttemptsService

    override fun onApplicationEvent(event: AuthenticationSuccessEvent) {
        val auth = event.authentication.details as WebAuthenticationDetails

        loginAttemptsService.loginSucceeded(auth.remoteAddress)
    }

}