package com.music.awesomemusic.services

import com.google.common.cache.CacheBuilder
import com.google.common.cache.CacheLoader
import com.google.common.cache.LoadingCache
import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.concurrent.ExecutionException
import java.util.concurrent.TimeUnit


@Service
class LoginAttemptsService {

    @Value("\${security.attempts.max-amount-attempts}")
    private lateinit var max_attempts: String


    private val _logger = Logger.getLogger(LoginAttemptsService::class.simpleName)

    private var attemptsCache: LoadingCache<String, Int> = CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.DAYS).build(object : CacheLoader<String, Int>() {
        override fun load(key: String): Int {
            return 0
        }
    })

    fun loginSucceeded(key: String) {
        attemptsCache.invalidate(key)
    }

    fun loginFailed(key: String) {
        var attempts = 0
        attempts = try {
            attemptsCache.get(key)
        } catch (e: ExecutionException) {
            _logger.info("new key")
            0
        }

        attempts++

        _logger.info(attempts)
        attemptsCache.put(key, attempts)
    }

    fun isBlocked(key: String?): Boolean {
        return try {
            attemptsCache[key!!] >= max_attempts.toInt()
        } catch (e: ExecutionException) {
            false
        }
    }

}