package com.mauquoi.moneymanagement.moneymanagement.rest.controllers

import com.mauquoi.moneymanagement.moneymanagement.domain.repositories.CryptoAssetRepository
import com.mauquoi.moneymanagement.moneymanagement.domain.services.CryptoAssetService
import com.mauquoi.moneymanagement.moneymanagement.mappers.CryptoAssetMapper
import com.mauquoi.moneymanagement.moneymanagement.rest.constants.URL
import com.mauquoi.moneymanagement.moneymanagement.rest.constants.URL.CryptoAssetUrl.CRYPTO_ASSETS
import com.mauquoi.moneymanagement.moneymanagement.rest.constants.URL.CryptoAssetUrl.CRYPTO_ASSET_BY_ID
import com.mauquoi.moneymanagement.moneymanagement.rest.dto.CryptoAssetDto
import com.mauquoi.moneymanagement.moneymanagement.rest.extension.toNullable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import javax.inject.Inject


@RestController
@CrossOrigin
class StockController @Inject constructor(
    private val cryptoAssetService: CryptoAssetService,
    private val cryptoAssetRepository: CryptoAssetRepository,
    private val cryptoAssetMapper: CryptoAssetMapper
) {

//    @GetMapping(CRYPTO_ASSETS)
//    fun getCryptoAssets(): ResponseEntity<List<CryptoAssetDto>> {
//        return ResponseEntity.ok(cryptoAssetMapper.toDtos(cryptoAssetService.getAllAssets()))
//    }
//
//    @GetMapping(CRYPTO_ASSET_BY_ID)
//    fun getCryptoAsset(@PathVariable(URL.PathVariable.CRYPTO_ID) cryptoId: String): ResponseEntity<CryptoAssetDto> {
//        return cryptoAssetRepository.findById(cryptoId).toNullable()
//            ?.let { ResponseEntity.ok(cryptoAssetMapper.toDto(it)) }
//            ?: ResponseEntity.notFound().build()
//    }

}
