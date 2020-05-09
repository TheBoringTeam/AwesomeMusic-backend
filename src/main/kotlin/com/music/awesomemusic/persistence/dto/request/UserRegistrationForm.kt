package com.music.awesomemusic.persistence.dto.request

data class UserRegistrationForm(val username: String, val password: String, val email: String, val isCollective: Boolean)