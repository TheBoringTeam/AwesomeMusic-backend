package com.music.awesomemusic.utils.errors

import java.lang.RuntimeException

class UsernameExists(msg: String) : RuntimeException(msg) {
}