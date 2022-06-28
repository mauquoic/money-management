package com.mauquoi.moneymanagement.moneymanagement.domain.services

import com.mauquoi.moneymanagement.moneymanagement.domain.entities.CryptoAsset
import com.mauquoi.moneymanagement.moneymanagement.domain.repositories.CryptoAssetRepository
import com.mauquoi.moneymanagement.moneymanagement.gateways.CoinGeckoGateway
import com.mauquoi.moneymanagement.moneymanagement.rest.extension.toNullable
import org.springframework.stereotype.Service
import javax.inject.Inject

@Service
class CryptoAssetService @Inject constructor(
    private val cryptoAssetRepository: CryptoAssetRepository,
    private val coinGeckoGateway: CoinGeckoGateway
) {

    fun loadDailyPrices() {
        for (page in 1..8) {
            val coinList: MutableList<CryptoAsset>? = coinGeckoGateway.loadGreatestCoins(page).collectList().block()
            coinList?.map {
                cryptoAssetRepository.findById(it.id).toNullable()?.also { coin -> coin.price = it.price } ?: it
            }
                ?.map { it.addNewSnapshot() }
                ?.let { cryptoAssetRepository.saveAll(it) }
        }
    }

    fun getAllAssets(): List<CryptoAsset> {
        return cryptoAssetRepository.findAllByOrderByMarketCapRank()
    }
}