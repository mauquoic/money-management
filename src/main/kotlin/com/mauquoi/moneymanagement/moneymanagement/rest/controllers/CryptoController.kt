package com.mauquoi.moneymanagement.moneymanagement.rest.controllers

import com.mauquoi.moneymanagement.moneymanagement.domain.services.CryptoService
import com.mauquoi.moneymanagement.moneymanagement.rest.constants.URL.CryptoUrl.CRYPTOS
import com.mauquoi.moneymanagement.moneymanagement.rest.constants.URL.CryptoUrl.CRYPTO_BY_ID
import com.mauquoi.moneymanagement.moneymanagement.rest.constants.URL.PathVariable.CRYPTO_ID
import com.mauquoi.moneymanagement.moneymanagement.rest.dto.CryptoPositionDto
import com.mauquoi.moneymanagement.moneymanagement.rest.dto.CryptoUpdateDto
import com.mauquoi.moneymanagement.moneymanagement.rest.extension.toDomain
import com.mauquoi.moneymanagement.moneymanagement.rest.extension.toDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.inject.Inject


@RestController
@CrossOrigin
class CryptoController @Inject constructor(private val cryptoPositionService: CryptoService) {
    @GetMapping(CRYPTO_BY_ID)
    fun getCryptoPosition(@PathVariable(CRYPTO_ID) cryptoPositionId: UUID): ResponseEntity<CryptoPositionDto> {
        return ResponseEntity.ok(cryptoPositionService.getCryptoPosition(cryptoPositionId).toDto())
    }

    @GetMapping(CRYPTOS)
    fun getCryptoPositions(): ResponseEntity<List<CryptoPositionDto>> {
        return ResponseEntity.ok(cryptoPositionService.getCryptoPositions().map { it.toDto() })
    }

    @PutMapping(CRYPTO_BY_ID)
    fun editCryptoPosition(
        @PathVariable(CRYPTO_ID) cryptoPositionId: UUID, @RequestBody cryptoPositionDto: CryptoPositionDto
    ): ResponseEntity<Nothing> {
        cryptoPositionService.editCryptoPosition(cryptoPositionId, cryptoPositionDto.toDomain())
        return ResponseEntity.noContent().build()
    }

    @PostMapping("$CRYPTO_BY_ID/update")
    fun updateCryptoPosition(
        @PathVariable(CRYPTO_ID) cryptoPositionId: UUID, @RequestBody updateDto: CryptoUpdateDto
    ): ResponseEntity<Nothing> {
        cryptoPositionService.updateCryptoPosition(
            cryptoPositionId, updateDto.newBalance, updateDto.increase, updateDto.changeType
        )
        return ResponseEntity.noContent().build()
    }

    @PostMapping(CRYPTOS)
    fun createCryptoPosition(@RequestBody cryptoPositionDto: CryptoPositionDto): ResponseEntity<CryptoPositionDto> {
        val cryptoPosition = cryptoPositionService.createCryptoPosition(cryptoPositionDto.toDomain())
        return ResponseEntity.status(HttpStatus.CREATED).body(cryptoPosition.toDto())
    }
}
