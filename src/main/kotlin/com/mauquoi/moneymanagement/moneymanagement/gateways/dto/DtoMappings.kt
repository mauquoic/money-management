package com.mauquoi.moneymanagement.moneymanagement.gateways.dto

import com.mauquoi.moneymanagement.moneymanagement.domain.entities.CryptoAsset

fun CryptoAssetDto.toDomain() = CryptoAsset(
    id = this.id,
    name = this.name,
    symbol = this.symbol.uppercase(),
    marketCapRank = this.marketCapRank,
    price = this.price
)