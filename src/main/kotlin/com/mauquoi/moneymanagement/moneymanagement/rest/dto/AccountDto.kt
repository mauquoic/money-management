package com.mauquoi.moneymanagement.moneymanagement.rest.dto

import java.time.LocalDate
import java.util.*
import javax.validation.constraints.NotNull

data class AccountDto(
    val id: UUID? = null,
    @NotNull val name: String,
    @NotNull val amount: Double,
    @NotNull val currency: Currency,
    val description: String? = null,
    val addedOn: LocalDate? = null
)