package com.mauquoi.moneymanagement.moneymanagement.domain.services

import com.mauquoi.moneymanagement.moneymanagement.domain.entities.CryptoAsset
import com.mauquoi.moneymanagement.moneymanagement.domain.repositories.CryptoAssetRepository
import com.mauquoi.moneymanagement.moneymanagement.gateways.CoinGeckoGateway
import com.mauquoi.moneymanagement.moneymanagement.rest.extension.toNullable
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.inject.Inject

@Service
class CryptoAssetService @Inject constructor(
    private val cryptoAssetRepository: CryptoAssetRepository,
    private val coinGeckoGateway: CoinGeckoGateway,
    @Value("\${service.coingecko.pages:8}") private val pages: Int
) {

    @Transactional
    fun loadDailyPrices() {
        for (page in 1..pages) {
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