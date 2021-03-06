package com.music.awesomemusic.persistence.domain

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "email_tokens")
class EmailVerificationToken(
        @Column(name = "token")
        val token: String,

        @OneToOne(targetEntity = AwesomeAccount::class, fetch = FetchType.EAGER, cascade = [CascadeType.REMOVE])
        @JoinColumn(nullable = false, name = "account_id") val account: AwesomeAccount
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