package com.music.awesomemusic.services

import com.music.awesomemusic.models.AwesomeUser
import com.music.awesomemusic.repositories.IUserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService {
    @Autowired
    lateinit var userRepository: IUserRepository

    fun getUserByUsername(username: String): AwesomeUser? {

        return userRepository.getByUsername(username)
    }

}