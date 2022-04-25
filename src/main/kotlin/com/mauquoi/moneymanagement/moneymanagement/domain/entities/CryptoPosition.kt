package com.mauquoi.moneymanagement.moneymanagement.domain.entities

import com.mauquoi.moneymanagement.moneymanagement.domain.models.ChangeType
import org.hibernate.annotations.GenericGenerator
import java.time.LocalDate
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "crypto_position")
data class CryptoPosition(
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    val id: UUID? = null,
    @Column(name = "name", nullable = false) val name: String,
    @Column(name = "amount", nullable = false) val amount: Double,
    @Column(name = "description") val description: String? = null,
    @Column(name = "added_on", nullable = false) val addedOn: LocalDate = LocalDate.now(),
    @Column(name = "edited_on", nullable = false) val editedOn: LocalDate = LocalDate.now(),
    @ManyToOne(fetch = FetchType.LAZY) var user: User? = null,
    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true, mappedBy = "cryptoPosition")
    val positionSnapshots: MutableList<CryptoPositionSnapshot> = mutableListOf()
) {
    fun createSnapshot(newBalance: Double, changeType: ChangeType?): CryptoPositionSnapshot {
        return CryptoPositionSnapshot(
            startAmount = this.amount,
            endAmount = newBalance,
            startDate = editedOn,
            type = changeType ?: ChangeType.OTHER,
            cryptoPosition = this
        )
    }

    fun updateBalance(balance: Double): CryptoPosition {
        return this.copy(amount = balance, editedOn = LocalDate.now())
    }

    fun setImmutableInfoFromCryptoPosition(cryptoPosition: CryptoPosition): CryptoPosition {
        return this.copy(
            id = cryptoPosition.id,
            positionSnapshots = cryptoPosition.positionSnapshots,
            addedOn = cryptoPosition.addedOn,
            user = cryptoPosition.user
        )
    }
}
