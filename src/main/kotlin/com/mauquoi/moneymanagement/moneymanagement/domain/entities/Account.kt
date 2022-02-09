package com.mauquoi.moneymanagement.moneymanagement.domain.entities

import java.time.LocalDate
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "account", indexes = [Index(columnList = "currency")])
data class Account(
    @Id val id: UUID = UUID.randomUUID(),
    @Column(name = "name", nullable = false) val name: String,
    @Column(name = "amount", nullable = false) val amount: Double,
    @Column(name = "currency", nullable = false) val currency: Currency,
    @Column(name = "description") val description: String? = null,
    @Column(name = "added_on", nullable = false) val addedOn: LocalDate = LocalDate.now()
)
