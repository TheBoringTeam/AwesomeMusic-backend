package com.music.awesomemusic.services

import com.music.awesomemusic.persistence.domain.AccountRole
import com.music.awesomemusic.persistence.domain.Role
import com.music.awesomemusic.repositories.IRoleRepository
import com.music.awesomemusic.utils.exceptions.basic.ResourceNotFoundException
import javassist.NotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RoleService() {


//    fun findByName(roleName: String): AccountRole {
//        return _roleRepository.findByRoleName(roleName)
//                .orElseThrow { ResourceNotFoundException("Role with name $roleName was not found") }
//    }
}