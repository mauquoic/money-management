package com.mauquoi.moneymanagement.moneymanagement.rest.dto

data class CryptoAssetDto(
    val id: String,
    val symbol: String,
    val name: String,
    val price: Double,
)
