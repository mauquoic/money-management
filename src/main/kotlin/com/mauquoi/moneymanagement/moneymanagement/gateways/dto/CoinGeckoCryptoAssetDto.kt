package com.mauquoi.moneymanagement.moneymanagement.gateways.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class CoinGeckoCryptoAssetDto(
    val id: String,
    val symbol: String,
    val name: String,
    @JsonProperty("current_price")
    val price: Double,
    @JsonProperty("market_cap_rank")
    val marketCapRank: Int,
)
