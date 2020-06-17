package com.music.awesomemusic.persistence.domain

//unknown, primary, secondary, bachelor, master, doctoral
enum class Education(val educationName: String) {
    UNKNOWN("Unknown"),
    PRIMARY("Primary"),
    SECONDARY("Secondary"),
    BACHELOR("Bachelor"),
    MASTER("Master"),
    DOCTORAL("Doctoral");
}