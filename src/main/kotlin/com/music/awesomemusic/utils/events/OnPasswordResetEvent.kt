package com.music.awesomemusic.utils.events

import com.music.awesomemusic.persistence.domain.AwesomeAccount
import org.springframework.context.ApplicationEvent
import java.util.*

class OnPasswordResetEvent(val account: AwesomeAccount, val locale: Locale, val appUrl: String) : ApplicationEvent(account)