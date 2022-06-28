package com.mauquoi.moneymanagement.moneymanagement.domain.repositories

import com.mauquoi.moneymanagement.moneymanagement.domain.entities.CryptoAsset
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CryptoAssetRepository: JpaRepository<CryptoAsset, String> {

    fun findBySymbol(symbol: String): CryptoAsset?

    fun findAllByOrderByMarketCapRank(): List<CryptoAsset>
}