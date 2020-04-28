package com.music.awesomemusic.utils

import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.context.support.ResourceBundleMessageSource

/**
 * Class for some general configuration.
 */
@Configuration
public class AppConfig {

    @Bean
    @Primary
    public fun provideMessageSource(): MessageSource {
        val source = ResourceBundleMessageSource()
        source.setBasename("messages/email_en")
        source.setUseCodeAsDefaultMessage(true)

        return source
    }

}