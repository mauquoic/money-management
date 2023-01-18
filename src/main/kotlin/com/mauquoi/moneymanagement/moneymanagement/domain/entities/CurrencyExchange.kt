package com.mauquoi.moneymanagement.moneymanagement.domain.entities

import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "currency_exchange")
data class CurrencyExchange(
    @Id val date: LocalDate,
    @ElementCollection
    @CollectionTable(name = "currency_mapping", joinColumns = [JoinColumn(name = "date")])
    @MapKeyColumn(name = "currency_conversion")
    @Column(name = "value")
    val currencyRates: Map<String, Double>
)