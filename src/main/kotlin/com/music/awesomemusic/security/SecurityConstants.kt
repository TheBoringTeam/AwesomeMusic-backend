package com.music.awesomemusic.security

enum class SecurityConstants(val value: String) {
    TOKEN_HEADER("AwToken"),
    //TODO : Add it(secret) to application.properties, bc it's fucking dangerous
    SECRET("RANDOMSECRET")
}