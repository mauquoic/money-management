package com.mauquoi.moneymanagement.moneymanagement.gateways

import com.mauquoi.moneymanagement.moneymanagement.domain.entities.CurrencyExchange
import com.mauquoi.moneymanagement.moneymanagement.gateways.dto.CurrencyLookupDto
import com.mauquoi.moneymanagement.moneymanagement.mappers.CurrencyMapper
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import javax.inject.Inject

@Component
class CurrencyGateway @Inject constructor(
    private val currencyConversionWebClient: WebClient,
    private val currencyMapper: CurrencyMapper
) {

    fun getRates(): Flux<CurrencyExchange> {
        return currencyConversionWebClient.get().uri { uriBuilder ->
            uriBuilder
                .path("/latest")
                .queryParam("base", "usd")
                .build()
        }
            .retrieve()
            .bodyToFlux(CurrencyLookupDto::class.java)
            .map { currencyMapper.toDomain(it) }
            .share()
    }
}