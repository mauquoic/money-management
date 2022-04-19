package com.mauquoi.moneymanagement.moneymanagement.rest.dto

import java.time.LocalDate
import java.util.*

data class AccountSnapshotDto(
    val id: UUID,
    val balance: Double,
    val validFrom: LocalDate,
    val validTo: LocalDate
)
