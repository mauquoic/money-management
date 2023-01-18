package com.mauquoi.moneymanagement.moneymanagement.domain.entities

import com.mauquoi.moneymanagement.moneymanagement.domain.models.AccountType
import org.hibernate.annotations.GenericGenerator
import java.time.LocalDate
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "account", indexes = [Index(columnList = "currency")])
data class Account(
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    val id: UUID? = null,
    @Column(name = "name", nullable = false) val name: String,
    @Column(name = "balance", nullable = false) val balance: Double,
    @Column(name = "currency", nullable = false) val currency: Currency,
    @Column(name = "liquid", nullable = false) val liquid: Boolean = true,
    @Column(name = "description") val description: String? = null,
    @Column(name = "type", nullable = false) @Enumerated(value = EnumType.STRING) val type: AccountType = AccountType.UNDEFINED,
    @Column(name = "added_on", nullable = false) val addedOn: LocalDate = LocalDate.now(),
    @Column(name = "edited_on", nullable = false) val editedOn: LocalDate = LocalDate.now(),
    @ManyToOne(fetch = FetchType.LAZY) var user: User? = null,
    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true, mappedBy = "account")
    val accountSnapshots: MutableList<AccountSnapshot> = mutableListOf()
) {
    fun createSnapshot(): AccountSnapshot {
        return AccountSnapshot(
            balance = this.balance,
            validFrom = editedOn,
            account = this
        )
    }

    fun updateBalance(balance: Double): Account {
        return this.copy(balance = balance, editedOn = LocalDate.now())
    }

    fun setImmutableInfoFromAccount(account: Account): Account {
        return this.copy(
            id = account.id,
            accountSnapshots = account.accountSnapshots,
            addedOn = account.addedOn,
            user = account.user
        )
    }
}
