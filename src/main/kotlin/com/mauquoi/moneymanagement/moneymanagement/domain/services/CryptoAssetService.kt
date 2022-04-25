package com.mauquoi.moneymanagement.moneymanagement.domain.services

import com.mauquoi.moneymanagement.moneymanagement.domain.repositories.CryptoAssetRepository
import org.springframework.stereotype.Service
import javax.inject.Inject

@Service
class CryptoAssetService @Inject constructor(private val cryptoAssetRepository: CryptoAssetRepository) {

    fun loadDailyPrices(){

    }
}