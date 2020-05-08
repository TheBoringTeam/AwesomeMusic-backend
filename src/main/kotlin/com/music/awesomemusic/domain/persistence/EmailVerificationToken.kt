package com.music.awesomemusic.domain.persistence

import com.music.awesomemusic.domain.persistence.AwesomeUser
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "email_tokens")
class EmailVerificationToken(
        @Column(name = "token")
        val token: String,

        @OneToOne(targetEntity = AwesomeUser::class, fetch = FetchType.EAGER)
        @JoinColumn(nullable = false, name = "user_id") val user: AwesomeUser
) {
    @Transient
    private val EXPIRATION = 60 * 24

    @Column(name = "expiry_date")
    val expiryDate: Date = calculateExpiryDate(EXPIRATION)

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0

    private fun calculateExpiryDate(expiryTimeInMinutes: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = Date().time
        calendar.add(Calendar.MINUTE, expiryTimeInMinutes)
        return Date(calendar.time.time)
    }

}