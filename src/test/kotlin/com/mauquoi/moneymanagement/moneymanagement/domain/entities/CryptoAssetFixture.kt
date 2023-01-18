package com.mauquoi.moneymanagement.moneymanagement.domain.entities

class CryptoAssetFixture {
    companion object {
        fun cryptoAsset(
            id: String = "BTC",
            symbol: String = "BTC",
            name: String = "Bitcoin",
            marketCapRank: Int = 1,
            latestPrice: Double = 20000.0,
            price: Double = 20000.0,
            priceHistory: MutableList<CryptoAssetPrice> = mutableListOf(),
            positions: MutableList<CryptoPosition> = mutableListOf()
        ): CryptoAsset = CryptoAsset(
            id = id,
            symbol = symbol,
            name = name,
            marketCapRank = marketCapRank,
            latestPrice = latestPrice,
            price = price,
            priceHistory = priceHistory,
            positions = positions
        )
    }
}