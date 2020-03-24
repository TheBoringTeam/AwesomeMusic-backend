package com.music.awesomemusic.repositories

import com.music.awesomemusic.models.AwesomeUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface IUserRepository : CrudRepository<AwesomeUser, Long> {
    @Query("SELECT u FROM AwesomeUser u")
    fun getAll(): List<AwesomeUser>


    @Query("SELECT u FROM AwesomeUser u WHERE u.username = :username")
    fun getByUsername(@Param("username") username: String): AwesomeUser?
}