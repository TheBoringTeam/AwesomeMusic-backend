package com.music.awesomemusic.utils.errors

import java.lang.RuntimeException

class UsernameExistsException(msg: String) : RuntimeException(msg) {
}