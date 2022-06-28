package com.mauquoi.moneymanagement.moneymanagement.rest.controllers

import com.mauquoi.moneymanagement.moneymanagement.domain.entities.CryptoAsset
import com.mauquoi.moneymanagement.moneymanagement.domain.services.CryptoAssetService
import com.mauquoi.moneymanagement.moneymanagement.rest.constants.URL.CryptoAssetUrl.CRYPTO_ASSETS
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import javax.inject.Inject


@RestController
@CrossOrigin
class CryptoAssetController @Inject constructor(private val cryptoAssetService: CryptoAssetService) {

    @GetMapping(CRYPTO_ASSETS)
    // TODO: 25.04.22 better DTO
    fun getCryptoAssets(): ResponseEntity<List<CryptoAsset>> {
        return ResponseEntity.ok(cryptoAssetService.getAllAssets())
    }

}
