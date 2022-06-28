package com.mauquoi.moneymanagement.moneymanagement.stub

import com.mauquoi.moneymanagement.moneymanagement.domain.repositories.CryptoAssetRepository
import com.mauquoi.moneymanagement.moneymanagement.domain.services.CryptoAssetService
import org.springframework.context.annotation.Profile
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import javax.inject.Inject

@RestController
@CrossOrigin
@Profile("!prd")
class CryptoAssetStubController @Inject constructor(
    private val cryptoAssetService: CryptoAssetService,
    private val cryptoAssetRepository: CryptoAssetRepository
) {

    @PostMapping("/stubs/assets/load")
    fun loadAssets(): ResponseEntity<Any> {
        val assets = cryptoAssetService.loadDailyPrices()
        return ResponseEntity.ok(assets)
    }

    @GetMapping("/stubs/assets")
    fun getAssets(): ResponseEntity<Any> {
        return ResponseEntity.ok(cryptoAssetRepository.findAll())
    }
}