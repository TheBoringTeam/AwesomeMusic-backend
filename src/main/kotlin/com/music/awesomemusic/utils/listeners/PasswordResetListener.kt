package com.music.awesomemusic.utils.listeners

import com.music.awesomemusic.services.AccountService
import com.music.awesomemusic.utils.events.OnPasswordResetEvent
import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.ApplicationListener
import org.springframework.context.MessageSource
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Component
import java.util.*

@Component
class PasswordResetListener : ApplicationListener<OnPasswordResetEvent> {

    private val _logger = Logger.getLogger(PasswordResetListener::class.java)

    @Autowired
    lateinit var accountService: AccountService

    @Autowired
    lateinit var messages: MessageSource

    @Autowired
    lateinit var sender: JavaMailSender

    @Value("\${spring.mail.username}")
    lateinit var mailAddress: String

    @Value("\${spring.mail.ip}")
    lateinit var ip: String

    @Value("\${server.port}")
    lateinit var port: String

    override fun onApplicationEvent(event: OnPasswordResetEvent) {
        sendPasswordResetEmail(event)
    }

    fun sendPasswordResetEmail(event: OnPasswordResetEvent) {
        val account = event.account
        val token = UUID.randomUUID().toString()
        accountService.createPasswordResetToken(account, token)

        val recipientAddress = account.email

        val subject = "AwesomeMusic - Email Confirmation"

        // TODO: Here should be link to frontend
        val messageUrl = "http://redirect.to.front"

        var message = messages.getMessage("message.resetPassword", null, null, event.locale)

        requireNotNull(message) {
            _logger.error("Message account properties returned null message content!")
            throw NullPointerException("Reset password message is null")
        }

        message = message.replace("%nickname%", account.username)
        message = message.replace("%service_name%", "AweMusic")
        message = message.replace("%change_password_button%", messageUrl)

        val email = SimpleMailMessage()
        email.setTo(recipientAddress)
        email.setSubject(subject)
        email.setText(message)
        email.setFrom("no-reply@awemusic.eu")

        sender.send(email)
    }
}