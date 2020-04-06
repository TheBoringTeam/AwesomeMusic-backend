package com.music.awesomemusic.services

import com.music.awesomemusic.domain.Role
import com.music.awesomemusic.domain.dto.UserRegistrationForm
import com.music.awesomemusic.models.AwesomeUser
import com.music.awesomemusic.repositories.IRoleRepository
import com.music.awesomemusic.repositories.IUserRepository
import com.music.awesomemusic.utils.errors.UsernameExists
import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService {

    private val logger = Logger.getLogger(UserService::class.java)

    @Autowired
    lateinit var userRepository: IUserRepository

    @Autowired
    lateinit var roleRepository: IRoleRepository

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

    fun createUser(userFormDto: UserRegistrationForm) {
        try {
            logger.debug("Creating user was started")
            if (userRepository.existsByUsername(userFormDto.username)) {
                logger.debug("User with username ${userFormDto.username} already exists")
                throw UsernameExists("Provided username is already taken")
            }

            logger.debug("Find a USER role for the current user")
            val role = roleRepository.findByRoleName("USER")

            val user = AwesomeUser(userFormDto.username, passwordEncoder.encode(userFormDto.password))

            // TODO : May be some optimization here (IDK rly)
            val roles = user.roles as ArrayList<Role>
            if (role.isPresent) {
                roles.add(role.get())
                logger.debug("Role for user ${userFormDto.username} was attached")
            }

            user.roles = roles
            userRepository.save(user)
            logger.debug("User ${user.username} was successfully created")
        } catch (e: Exception) {
            logger.error("Error while creating a new user: ${e.message}")
            throw e
        }
    }
}