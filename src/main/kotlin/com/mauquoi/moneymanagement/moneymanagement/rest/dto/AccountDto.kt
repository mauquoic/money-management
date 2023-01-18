package com.mauquoi.moneymanagement.moneymanagement.rest.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.mauquoi.moneymanagement.moneymanagement.domain.models.AccountType
import java.time.LocalDate
import java.util.*
import javax.validation.constraints.NotNull

data class AccountDto(
    val id: UUID? = null,
    @NotNull val name: String,
    @NotNull val balance: Double,
    @NotNull val currency: Currency,
    val description: String? = null,
    val addedOn: LocalDate? = null,
    val liquid: Boolean = true,
    val type: AccountType = AccountType.UNDEFINED,
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    val snapshots: List<AccountSnapshotDto> = listOf()
)