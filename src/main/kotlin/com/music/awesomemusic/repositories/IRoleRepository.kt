package com.music.awesomemusic.repositories

import com.music.awesomemusic.domain.Role
import com.music.awesomemusic.models.AwesomeUser
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface IRoleRepository : CrudRepository<Role, Long> {
    fun findByRoleName(roleName: String) : Optional<Role>
}