package com.mauquoi.moneymanagement.moneymanagement.gateways

import com.mauquoi.moneymanagement.moneymanagement.domain.entities.CryptoAsset
import com.mauquoi.moneymanagement.moneymanagement.gateways.dto.CoinGeckoCryptoAssetDto
import com.mauquoi.moneymanagement.moneymanagement.mappers.CryptoAssetMapper
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import javax.inject.Inject

@Component
class CoinGeckoGateway @Inject constructor(
    private val coinGeckoWebClient: WebClient,
    private val cryptoAssetMapper: CryptoAssetMapper
) {

    fun loadGreatestCoins(page: Int): Flux<CryptoAsset> {

        return coinGeckoWebClient.get().uri { uriBuilder ->
            uriBuilder
                .path("/coins/markets")
                .queryParam("vs_currency", "usd")
                .queryParam("order", "market_cap_desc")
                .queryParam("per_page", "250")
                .queryParam("sparkline", "false")
                .queryParam("page", page)
                .build()
        }
            .retrieve()
            .bodyToFlux(CoinGeckoCryptoAssetDto::class.java)
            .map { cryptoAssetMapper.toDomain(it) }
            .share()
    }
}