package com.music.awesomemusic.services

import com.music.awesomemusic.persistence.domain.AwesomeAccount
import com.music.awesomemusic.repositories.IAccountRepository
import com.music.awesomemusic.utils.exceptions.user.AccountNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class AccountService {
    @Autowired
    lateinit var accountRepository: IAccountRepository

    fun findByUsername(username: String): AwesomeAccount {
        return accountRepository.findByUsername(username).orElseThrow {
            UsernameNotFoundException("Username was not found")
        }
    }
}