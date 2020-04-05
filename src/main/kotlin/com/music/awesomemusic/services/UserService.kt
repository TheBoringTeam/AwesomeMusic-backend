package com.music.awesomemusic.services

import com.music.awesomemusic.domain.dto.UserFormDto
import com.music.awesomemusic.models.AwesomeUser
import com.music.awesomemusic.repositories.IUserRepository
import com.music.awesomemusic.utils.errors.UsernameExists
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService {
    @Autowired
    lateinit var userRepository: IUserRepository

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    fun findByUsername(username: String): AwesomeUser? {
        return userRepository.getByUsername(username)
    }

    fun findById(id: Long): AwesomeUser? {
        return userRepository.getById(id)
    }

    fun existsByUsername(username: String): Boolean {
        return userRepository.existsByUsername(username)
    }

    fun createUser(userFormDto: UserFormDto) {
        if (userRepository.existsByUsername(userFormDto.username)) {
            throw UsernameExists("Provided username is already taken")
        }

        val user = AwesomeUser(userFormDto.username, passwordEncoder.encode(userFormDto.password))
        userRepository.save(user)
    }
}