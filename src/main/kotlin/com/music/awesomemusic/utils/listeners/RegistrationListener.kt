package com.music.awesomemusic.utils.listeners

import com.music.awesomemusic.services.AccountService
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

        val subject = "AwesomeMusic - Email Conformation"
        val confUrl = "http://$ip:$port/api/user/registrationConfirm?token=$token"

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