package com.music.awesomemusic.utils.listeners

import com.music.awesomemusic.services.UserService
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

    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var messages: MessageSource

    @Autowired
    lateinit var sender : JavaMailSender

    @Value("\${spring.mail.username}")
    lateinit var mailAddress : String

    @Value("\${spring.mail.app.url}")
    lateinit var urlAddress : String

    override fun onApplicationEvent(event: OnRegistrationCompleteEvent) {
        this.confirmRegistration(event)
    }

    private fun confirmRegistration(event: OnRegistrationCompleteEvent) {
        val user = event.user
        val token = UUID.randomUUID().toString()
        userService.createEmailVerificationToken(user, token)

        val recipientAddress = user.email

        val subject = "AwesomeMusic - Email Conformation"
        val confUrl = "http://$urlAddress/api/user/registrationConfirm?token=$token"

        // TODO : Handle different languages
        val message = messages.getMessage("message.regSucc", null, "You registered successfully." +
                " We will send you a confirmation message to your email account.", event.locale)

        val email = SimpleMailMessage()
        email.setTo(recipientAddress)
        email.setSubject(subject)
        email.setText("$message \r\n $confUrl")
        email.setFrom(mailAddress)

        sender.send(email)
    }

}