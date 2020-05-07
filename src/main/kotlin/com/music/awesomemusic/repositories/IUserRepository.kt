package com.music.awesomemusic.repositories

import com.music.awesomemusic.domain.AwesomeUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface IUserRepository : CrudRepository<AwesomeUser, Long> {

    @Query("select u FROM AwesomeUser u where u.id = :id")
    fun getById(@Param("id") id: Long): AwesomeUser?


    @Query("SELECT u FROM AwesomeUser u WHERE u.username = :username")
    fun getByUsername(@Param("username") username: String): Optional<AwesomeUser>

    fun existsByUsername(username: String): Boolean

    fun existsByEmail(email: String): Boolean
}