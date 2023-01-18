package com.mauquoi.moneymanagement.moneymanagement.mappers

import com.mauquoi.moneymanagement.moneymanagement.domain.entities.Account
import com.mauquoi.moneymanagement.moneymanagement.mappers.config.CentralConfig
import com.mauquoi.moneymanagement.moneymanagement.rest.dto.AccountDto
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings

@Mapper(config = CentralConfig::class, uses = [AccountSnapshotMapper::class])
interface AccountMapper {

    @Mappings(
        Mapping(source = "accountSnapshots", target = "snapshots")
    )
    fun toDto(account: Account): AccountDto

    fun toDtos(account: List<Account>): List<AccountDto>
}