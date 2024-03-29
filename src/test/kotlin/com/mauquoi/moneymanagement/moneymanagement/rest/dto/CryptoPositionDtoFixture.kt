package com.mauquoi.moneymanagement.moneymanagement.rest.dto

class CryptoPositionDtoFixture {
    companion object {
        fun cryptoPositionDto(
            name: String = "name",
            description: String? = null,
            amount: Double = 1000.0,
            assetSymbol: String = "BTC"
        ): CryptoPositionDto {
            return CryptoPositionDto(
                name = name,
                amount = amount,
                description = description,
                assetSymbol = assetSymbol
            )
        }
    }
}