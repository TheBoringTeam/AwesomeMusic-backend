package com.music.awesomemusic.utils.listeners

import com.music.awesomemusic.services.AccountService
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
class RegistrationListener : ApplicationListener<OnRegistrationCompleteEvent> {

    private val _logger = Logger.getLogger(RegistrationListener::class.java)

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


    override fun onApplicationEvent(event: OnRegistrationCompleteEvent) {
        this.confirmRegistration(event)
    }

    private fun confirmRegistration(event: OnRegistrationCompleteEvent) {
        val account = event.account
        val token = UUID.randomUUID().toString()
        accountService.createEmailVerificationToken(account, token)

        val recipientAddress = account.email

        val subject = "AwesomeMusic - Email Confirmation"
        val confUrl = "http://$ip:$port/api/user/registrationConfirm?token=$token"

        // TODO : Handle different languages
        var message = messages.getMessage("message.regSucc", null, "You registered successfully." +
                " We will send you a confirmation message to your email account.", event.locale)

        requireNotNull(message) {
            _logger.error("Message email properties returned null message content!")
            throw NullPointerException("Registration message is null")
        }

        message = message.replace("%nickname%", account.username)
        message = message.replace("%service_name%", "AweMusic")
        message = message.replace("%activation_link%", confUrl)

        val email = SimpleMailMessage()
        email.setTo(recipientAddress)
        email.setSubject(subject)
        email.setText(message)
        email.setFrom("no-reply@awemusic.eu")

        sender.send(email)
    }

}