package com.music.awesomemusic.utils.listeners

import com.music.awesomemusic.utils.events.OnPasswordResetEvent
import org.springframework.context.ApplicationListener

class PasswordResetListener : ApplicationListener<OnPasswordResetEvent> {
    override fun onApplicationEvent(event: OnPasswordResetEvent) {

    }

}