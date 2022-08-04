package com.mauquoi.moneymanagement.moneymanagement.gateways.dto

class CryptoAssetDtoFixture {
    companion object {
        fun cryptoAssetDto(
            id: String = "BTC",
            symbol: String = "BTC",
            name: String = "Bitcoin",
            price: Double = 10.0,
            marketCapRank: Int = 1
        ): CoinGeckoCryptoAssetDto {
            return CoinGeckoCryptoAssetDto(
                id = id,
                name = name,
                symbol = symbol,
                price = price,
                marketCapRank = marketCapRank
            )
        }

        fun cryptoList(): List<CoinGeckoCryptoAssetDto> {
            return listOf(
                cryptoAssetDto(),
                cryptoAssetDto(id = "ETH", symbol = "ETH", marketCapRank = 2, price = 5.0, name = "Ethereum"),
                cryptoAssetDto(id = "BNB", symbol = "BNB", marketCapRank = 2, price = 3.0, name = "Binance Coin")
            )
        }
    }
}