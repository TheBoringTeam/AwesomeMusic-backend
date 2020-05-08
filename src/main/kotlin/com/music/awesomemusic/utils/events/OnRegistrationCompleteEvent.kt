package com.music.awesomemusic.utils.listeners

import com.music.awesomemusic.domain.persistence.AwesomeUser
import org.springframework.context.ApplicationEvent
import java.util.*

class OnRegistrationCompleteEvent(val user : AwesomeUser, val locale: Locale, val appUrl : String) : ApplicationEvent(user) {

}