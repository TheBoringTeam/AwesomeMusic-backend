package com.music.awesomemusic.persistence.domain

//unknown, primary, secondary, bachelor, master, doctoral
enum class Education(education: String) {
    UNKNOWN("Unknown"),
    PRIMARY("Primary"),
    SECONDARY("Secondary"),
    BACHELOR("Bachelor"),
    MASTER("Master"),
    DOCTORAL("Doctoral");
}