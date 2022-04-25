package com.mauquoi.moneymanagement.moneymanagement.rest.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.mauquoi.moneymanagement.moneymanagement.domain.models.ChangeType
import java.time.LocalDate
import java.util.*
import javax.validation.constraints.NotNull

data class CryptoPositionDto(
    val id: UUID? = null,
    @NotNull val name: String,
    @NotNull val amount: Double,
    val description: String? = null,
    val addedOn: LocalDate? = null,
    val editedOn: LocalDate? = null,
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    val positionSnapshots: List<CryptoPositionSnapshotDto> = listOf()
)

data class CryptoPositionSnapshotDto(
    val id: UUID? = null,
    @NotNull val startAmount: Double,
    @NotNull val endAmount: Double,
    @NotNull val startDate: LocalDate,
    @NotNull val endDate: LocalDate = LocalDate.now(),
    @NotNull val type: ChangeType = ChangeType.OTHER,
    val increase: Double?
)

data class CryptoUpdateDto(
    val newBalance: Double?,
    val increase: Double?,
    @NotNull val changeType: ChangeType
)
