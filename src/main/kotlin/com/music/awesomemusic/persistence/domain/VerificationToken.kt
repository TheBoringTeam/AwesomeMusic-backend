package com.music.awesomemusic.persistence.domain

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "tokens")
class VerificationToken(
        @Column(name = "token")
        val token: String,

        @OneToOne(targetEntity = AwesomeAccount::class, fetch = FetchType.EAGER)
        @JoinColumn(nullable = false, name = "account_uuid") val account: AwesomeAccount,

        @Column(name = "tokenType", nullable = false)
        val tokenType: TokenType
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