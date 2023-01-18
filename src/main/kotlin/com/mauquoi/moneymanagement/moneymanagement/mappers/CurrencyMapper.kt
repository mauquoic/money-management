package com.mauquoi.moneymanagement.moneymanagement.mappers

import com.mauquoi.moneymanagement.moneymanagement.domain.entities.CurrencyExchange
import com.mauquoi.moneymanagement.moneymanagement.gateways.dto.CurrencyLookupDto
import com.mauquoi.moneymanagement.moneymanagement.mappers.config.CentralConfig
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings

@Mapper(config = CentralConfig::class)
interface CurrencyMapper {

    @Mappings(
        Mapping(source = "rates", target = "currencyRates")
    )
    fun toDomain(currencyLookupDto: CurrencyLookupDto): CurrencyExchange
}