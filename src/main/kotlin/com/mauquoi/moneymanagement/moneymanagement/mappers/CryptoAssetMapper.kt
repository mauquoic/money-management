package com.mauquoi.moneymanagement.moneymanagement.mappers

import com.mauquoi.moneymanagement.moneymanagement.domain.entities.Account
import com.mauquoi.moneymanagement.moneymanagement.domain.entities.CryptoAsset
import com.mauquoi.moneymanagement.moneymanagement.domain.entities.User
import com.mauquoi.moneymanagement.moneymanagement.gateways.dto.CoinGeckoCryptoAssetDto
import com.mauquoi.moneymanagement.moneymanagement.mappers.config.CentralConfig
import com.mauquoi.moneymanagement.moneymanagement.rest.dto.AccountDto
import com.mauquoi.moneymanagement.moneymanagement.rest.dto.CryptoAssetDto
import com.mauquoi.moneymanagement.moneymanagement.rest.dto.UserDto
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings

@Mapper(config = CentralConfig::class, imports = [ArrayList::class])
interface CryptoAssetMapper {

    @Mappings(
        Mapping(source = "latestPrice", target = "price")
    )
    fun toDto(cryptoAsset: CryptoAsset): CryptoAssetDto

    fun toDtos(assets: List<CryptoAsset>): List<CryptoAssetDto>

    @Mappings(
        Mapping(target = "latestPrice", source = "price"),
        // TODO: write "upper" method centrally
        Mapping(target = "symbol", expression = "java(assetDto.getSymbol().toUpperCase())"),
        Mapping(target = "priceHistory", expression = "java(new ArrayList<>())"),
        Mapping(target = "positions", expression = "java(new ArrayList<>())"),
    )
    fun toDomain(assetDto: CoinGeckoCryptoAssetDto): CryptoAsset
}