package com.mauquoi.moneymanagement.moneymanagement.domain.services

import com.mauquoi.moneymanagement.moneymanagement.domain.entities.CurrencyExchange
import com.mauquoi.moneymanagement.moneymanagement.domain.repositories.CurrencyRepository
import com.mauquoi.moneymanagement.moneymanagement.gateways.CurrencyGateway
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.util.Currency
import javax.inject.Inject

@Component
class CurrencyService @Inject constructor(private val currencyRepository: CurrencyRepository, private val currencyGateway: CurrencyGateway) {

    @Transactional
    fun storeConversionRates(): CurrencyExchange {
        val rates = currencyGateway.getRates().collectList().block()!![0]
        currencyRepository.save(rates)
        return rates
    }

    fun getConversionRate(currency: Currency): Double {
        return currencyRepository.getById(LocalDate.now()).currencyRates[currency.currencyCode]!!
    }
}