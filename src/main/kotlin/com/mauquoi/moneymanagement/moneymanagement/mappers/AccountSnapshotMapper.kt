package com.mauquoi.moneymanagement.moneymanagement.mappers

import com.mauquoi.moneymanagement.moneymanagement.domain.entities.AccountSnapshot
import com.mauquoi.moneymanagement.moneymanagement.mappers.config.CentralConfig
import com.mauquoi.moneymanagement.moneymanagement.rest.dto.AccountSnapshotDto
import org.mapstruct.Mapper

@Mapper(config = CentralConfig::class)
interface AccountSnapshotMapper {

    fun toDto(snapshot: AccountSnapshot): AccountSnapshotDto

    fun toDtos(snapshots: List<AccountSnapshot>): List<AccountSnapshotDto>
}