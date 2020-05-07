package com.music.awesomemusic.services

import com.music.awesomemusic.domain.EmailVerificationToken
import com.music.awesomemusic.domain.Role
import com.music.awesomemusic.domain.dto.UserRegistrationForm
import com.music.awesomemusic.domain.AwesomeUser
import com.music.awesomemusic.repositories.IRoleRepository
import com.music.awesomemusic.repositories.ITokenRepository
import com.music.awesomemusic.repositories.IUserRepository
import com.music.awesomemusic.utils.errors.UsernameExistsException
import javassist.NotFoundException
import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import java.util.*
import kotlin.collections.ArrayList

@Service
class UserService {

    private val logger = Logger.getLogger(UserService::class.java)

    @Autowired
    lateinit var userRepository: IUserRepository

    @Autowired
    lateinit var roleRepository: IRoleRepository

    @Autowired
    lateinit var tokenRepository: ITokenRepository

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    fun findByUsername(username: String): AwesomeUser {
        //TODO: Refactor query throw part
        return userRepository.getByUsername(username).orElseThrow { NotFoundException("User with username was not found") }
    }

    fun findById(id: Long): AwesomeUser? {
        return userRepository.getById(id)
    }

    fun existsByUsername(username: String): Boolean {
        return userRepository.existsByUsername(username)
    }

    fun createUser(userFormDto: UserRegistrationForm): AwesomeUser {
        try {
            logger.debug("Creating user was started")

            val role = roleRepository.findByRoleName("USER")

            val user = AwesomeUser(userFormDto.username, passwordEncoder.encode(userFormDto.password), userFormDto.email)

            // TODO : May be some optimization here (IDK rly)
            // add role to new user
            val roles = user.roles as ArrayList<Role>
            if (role.isPresent) {
                roles.add(role.get())
                logger.debug("Role for user ${userFormDto.username} was attached")
            }
            user.roles = roles

            userRepository.save(user)

            logger.debug("User ${user.username} was successfully created")
            return user
        } catch (e: Exception) {
            logger.error("Error while creating a new user: ${e.message}")
            throw e
        }
    }

    fun createEmailVerificationToken(user: AwesomeUser, token: String) {
        val verificationToken = EmailVerificationToken(token, user)
        tokenRepository.save(verificationToken)
    }

    fun saveUser(user: AwesomeUser) {
        userRepository.save(user)
    }

    fun getVerificationToken(token: String): EmailVerificationToken? {
        return tokenRepository.findByToken(token)
    }

    fun existsByEmail(email: String): Boolean {
        return userRepository.existsByEmail(email)
    }
}