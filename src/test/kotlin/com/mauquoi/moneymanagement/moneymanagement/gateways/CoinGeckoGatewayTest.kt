package com.mauquoi.moneymanagement.moneymanagement.gateways

import com.mauquoi.moneymanagement.moneymanagement.domain.entities.CryptoAsset
import com.mauquoi.moneymanagement.moneymanagement.gateways.dto.CryptoAssetDto
import com.mauquoi.moneymanagement.moneymanagement.gateways.dto.CryptoAssetDtoFixture.Companion.cryptoList
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriBuilder
import reactor.core.publisher.Flux
import java.net.URI

@ExtendWith(MockKExtension::class)
internal class CoinGeckoGatewayTest {

    @MockK
    lateinit var webClient: WebClient

    @MockK
    lateinit var requestBodyUriSpec: WebClient.RequestBodyUriSpec

    @MockK
    lateinit var requestBodySpec: WebClient.RequestBodySpec

    @MockK
    lateinit var responseSpec: WebClient.ResponseSpec

    @InjectMockKs
    lateinit var coinGeckoGateway: CoinGeckoGateway

    @Test
    fun loadGreatestCoins() {
        every { webClient.get() } returns requestBodyUriSpec
        every { requestBodyUriSpec.uri(any<java.util.function.Function<UriBuilder, URI>>()) } returns requestBodySpec
        every { requestBodySpec.retrieve() } returns responseSpec
        every { responseSpec.bodyToFlux(CryptoAssetDto::class.java) } returns Flux.fromIterable(cryptoList())

        val greatestCoins = coinGeckoGateway.loadGreatestCoins(0)
        val coinsAsList = greatestCoins.collectList().block()

        assertAll(
            { assertThat(coinsAsList!!.size).isEqualTo(3) },
            { assertThat(coinsAsList!![0]).isInstanceOf(CryptoAsset::class.java) },
            { assertThat(coinsAsList!![0].id).isEqualTo("BTC") }
        )
    }
}