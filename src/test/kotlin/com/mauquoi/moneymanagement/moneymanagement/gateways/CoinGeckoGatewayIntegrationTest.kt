package com.mauquoi.moneymanagement.moneymanagement.gateways

import com.fasterxml.jackson.databind.ObjectMapper
import com.mauquoi.moneymanagement.moneymanagement.domain.entities.CryptoAsset
import com.mauquoi.moneymanagement.moneymanagement.gateways.dto.CryptoAssetDtoFixture.Companion.cryptoList
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import java.nio.charset.StandardCharsets
import javax.inject.Inject

@SpringBootTest
@ActiveProfiles("test")
internal class CoinGeckoGatewayIntegrationTest {

    private lateinit var server: MockWebServer

    @Inject
    private lateinit var coinGeckoGateway: CoinGeckoGateway

    private val objectMapper = ObjectMapper()

    @BeforeEach
    fun setUp() {
        server = MockWebServer()
        server.start(12345)
    }

    @Test
    fun loadGreatestCoins() {
        val cryptos = cryptoList()
        server.enqueue(
            MockResponse().setBody(
                objectMapper.writeValueAsString(cryptos)
            )
                .setResponseCode(200)
                .setHeader("Content-Type", "application/json")
        )
        val greatestCoins = coinGeckoGateway.loadGreatestCoins(1)

        val coinsAsList = greatestCoins.collectList().block()
        val request = server.takeRequest()

        assertAll(
            { assertThat(coinsAsList!!.size).isEqualTo(3) },
            { assertThat(coinsAsList!![0]).isInstanceOf(CryptoAsset::class.java) },
            { assertThat(coinsAsList!![0].id).isEqualTo("BTC") },
            { assertThat(request.getHeader(HttpHeaders.CONTENT_TYPE)).isEqualTo(MediaType.APPLICATION_JSON_VALUE) },
            { assertThat(request.getHeader(HttpHeaders.ACCEPT)).isEqualTo("${MediaType.APPLICATION_JSON}") },
            { assertThat(request.getHeader(HttpHeaders.ACCEPT_CHARSET)).isEqualTo(StandardCharsets.UTF_8.toString()) },
            { assertThat(request.requestUrl.toString()).isEqualTo("http://localhost:12345/coins/markets?vs_currency=usd&order=market_cap_desc&per_page=250&sparkline=false&page=1") }
        )
    }

    @AfterEach
    fun tearDown() {
        server.shutdown()
    }
}