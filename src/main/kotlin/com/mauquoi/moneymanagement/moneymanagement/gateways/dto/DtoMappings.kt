package com.mauquoi.moneymanagement.moneymanagement.gateways.dto

import com.mauquoi.moneymanagement.moneymanagement.domain.entities.CryptoAsset

fun CoinGeckoCryptoAssetDto.toDomain() = CryptoAsset(
    id = this.id,
    name = this.name,
    symbol = this.symbol.uppercase(),
    marketCapRank = this.marketCapRank,
    price = this.price,
    latestPrice = this.price
)