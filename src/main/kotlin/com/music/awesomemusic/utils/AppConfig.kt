package com.music.awesomemusic.utils

import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.context.support.ResourceBundleMessageSource
import org.springframework.web.context.request.RequestContextListener




/**
 * Class for some general configuration.
 */
@Configuration
class AppConfig {

    @Bean
    @Primary
    fun provideMessageSource(): MessageSource {
        val source = ResourceBundleMessageSource()
        source.setBasename("messages/account_en")
        source.setUseCodeAsDefaultMessage(true)

        return source
    }

    @Bean
    fun requestContextListener(): RequestContextListener? {
        return RequestContextListener()
    }

}