package com.mauquoi.moneymanagement.moneymanagement.stub

import com.mauquoi.moneymanagement.moneymanagement.domain.repositories.CurrencyRepository
import com.mauquoi.moneymanagement.moneymanagement.domain.services.CurrencyService
import org.springframework.context.annotation.Profile
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.util.Currency
import javax.inject.Inject

@RestController
@CrossOrigin
@Profile("!prd")
class CurrencyStubController @Inject constructor(
    private val currencyService: CurrencyService,
    private val currencyRepository: CurrencyRepository
) {

    @GetMapping("/stubs/currency-rates")
    fun getAssets(): ResponseEntity<Any> {
        return ResponseEntity.ok(currencyService.storeConversionRates())
    }

    @GetMapping("/stubs/currency-rates/{currency}")
    fun getAssets(@PathVariable(value = "currency") currency: Currency): ResponseEntity<Any> {
        return ResponseEntity.ok(currencyService.getConversionRate(currency))
    }
}