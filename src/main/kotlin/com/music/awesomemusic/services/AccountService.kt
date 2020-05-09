package com.music.awesomemusic.services

import com.music.awesomemusic.persistence.domain.AwesomeAccount
import com.music.awesomemusic.persistence.dto.request.UserRegistrationForm
import com.music.awesomemusic.repositories.IAccountRepository
import org.apache.log4j.Logger


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AccountService {

    private val _logger = Logger.getLogger(AccountService::class.java)

    @Autowired
    lateinit var accountRepository: IAccountRepository

    @Autowired
    lateinit var roleService: RoleService


    fun findByUsername(username: String): AwesomeAccount {
        return accountRepository.findByUsername(username).orElseThrow {
            UsernameNotFoundException("Username was not found")
        }
    }

//    fun createAccount(userFormDto: UserRegistrationForm): AwesomeAccount {
//        try {
//            _logger.debug("Creating user was started")
//
//            val role = roleService.findByName("USER")
//
//            val user = AwesomeAccount(userFormDto.username, passwordEncoder.encode(userFormDto.password), userFormDto.email,
//                    userFormDto.username, userFormDto.isCollective)
//
//            // TODO : May be some optimization here (IDK rly)
//            // add role to new user
//            val roles = user.roles as ArrayList<*>
//            roles.add(role)
//            user.roles = roles
//
//            userRepository.save(user)
//
//            logger.debug("User ${user.username} was successfully created")
//            return user
//        } catch (e: Exception) {
//            logger.error("Error while creating a new user: ${e.message}")
//            throw e
//        }
//    }
}