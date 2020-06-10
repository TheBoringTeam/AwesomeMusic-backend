package com.music.awesomemusic.persistence.domain

import javax.persistence.*

@Entity
@Table(name = "role_permission")
class RolePermission(
        @ManyToOne
        @JoinColumn(name = "role_id")
        var role: Role,

        @ManyToOne
        @JoinColumn(name = "permission_id", nullable = false)
        var permission: Permission
) {
}