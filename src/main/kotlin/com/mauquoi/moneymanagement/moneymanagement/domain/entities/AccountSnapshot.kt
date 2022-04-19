package com.mauquoi.moneymanagement.moneymanagement.domain.entities

import org.hibernate.annotations.GenericGenerator
import java.time.LocalDate
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "account_snapshot")
data class AccountSnapshot(
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    val id: UUID? = null,
    @Column(name = "balance", nullable = false) val balance: Double,
    @Column(name = "valid_from", nullable = false) val validFrom: LocalDate,
    @Column(name = "valid_to", nullable = false) val validTo: LocalDate = LocalDate.now(),
    @ManyToOne(fetch = FetchType.LAZY) val account: Account
    )