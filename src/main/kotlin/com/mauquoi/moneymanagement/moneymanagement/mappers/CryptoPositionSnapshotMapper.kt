package com.mauquoi.moneymanagement.moneymanagement.mappers

import com.mauquoi.moneymanagement.moneymanagement.domain.entities.CryptoPositionSnapshot
import com.mauquoi.moneymanagement.moneymanagement.mappers.config.CentralConfig
import com.mauquoi.moneymanagement.moneymanagement.rest.dto.CryptoPositionSnapshotDto
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(config = CentralConfig::class)
interface CryptoPositionSnapshotMapper {

    @Mapping(
        target = "increase",
        expression = "java(cryptoPositionSnapshot.getEndAmount() - cryptoPositionSnapshot.getStartAmount())"
    )
    fun toDto(cryptoPositionSnapshot: CryptoPositionSnapshot): CryptoPositionSnapshotDto
}