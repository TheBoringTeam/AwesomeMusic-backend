package com.music.awesomemusic.repositories

import com.music.awesomemusic.domain.domain.Role
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface IRoleRepository : CrudRepository<Role, Int> {
    fun findByRoleName(roleName: String) : Optional<Role>
}