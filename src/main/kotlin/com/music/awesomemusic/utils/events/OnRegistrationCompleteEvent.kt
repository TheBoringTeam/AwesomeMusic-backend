package com.music.awesomemusic.utils.listeners

import com.music.awesomemusic.persistence.domain.AwesomeAccount
import org.springframework.context.ApplicationEvent
import java.util.*

class OnRegistrationCompleteEvent(val account : AwesomeAccount, val locale: Locale, val appUrl : String) : ApplicationEvent(account) {

}