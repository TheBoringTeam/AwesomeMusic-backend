package com.music.awesomemusic.persistence.dto.response

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.music.awesomemusic.persistence.domain.AwesomeAccount

class AccountResponse constructor(
        @JsonIgnore
        val account: AwesomeAccount
) {
    @JsonProperty("public_name")
    val fullName: String = account.name

    @JsonProperty("uuid")
    val uuid: String = account.uuid.toString()

    @JsonProperty("is_musician_collective")
    val isCollective: Boolean = account.isCollective

    @JsonProperty("musician_biography")
    val bio: String? = account.biography

    @JsonProperty("birthday")
    val birthDay: String? = account.birthday.toString()

    @JsonProperty("deathday")
    val deathDay: String? = account.deathDay.toString()

    @JsonProperty("gender")
    val gender: String? = account.gender?.genderName

    @JsonProperty("education")
    val education: String? = account.education?.educationName

    @JsonProperty("country_name")
    val countryName: String? = account.country?.countryName

    @JsonProperty("language_name")
    val languageName: String? = account.language?.languageName

    @JsonProperty("followers")
    val followers: Long = account.followerCounter
}