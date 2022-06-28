package com.mauquoi.moneymanagement.moneymanagement.gateways

import io.netty.channel.ChannelOption
import io.netty.handler.timeout.ReadTimeoutHandler
import io.netty.handler.timeout.WriteTimeoutHandler
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.ClientRequest
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.ExchangeFilterFunction
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import reactor.netty.http.client.HttpClient
import java.nio.charset.StandardCharsets
import java.time.Duration
import java.util.concurrent.TimeUnit
import java.util.function.Consumer


@Configuration
class WebClientConfiguration {

    @Bean
    fun coinGeckoWebClient(@Value("\${service.coingecko.base-url}") baseUrl: String): WebClient {
        return WebClient.builder()
            .baseUrl(baseUrl)
            .filter(logRequest())
            .filter(logResponse())
            .clientConnector(ReactorClientHttpConnector(httpClient()))
            .codecs { configurer ->
                configurer
                    .defaultCodecs()
                    .maxInMemorySize(16 * 1024 * 1024)
            }
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .defaultHeader(HttpHeaders.ACCEPT, "${MediaType.APPLICATION_JSON}")
            .defaultHeader(HttpHeaders.ACCEPT_CHARSET, StandardCharsets.UTF_8.toString())
            .build()
    }

    @Bean
    fun httpClient(): HttpClient {
        return HttpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
            .responseTimeout(Duration.ofMillis(5000))
            .doOnConnected { conn ->
                conn.addHandlerLast(ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
                    .addHandlerLast(WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS))
            }
    }

    // TODO: 26.04.22 replace println with logger
    private fun logRequest(): ExchangeFilterFunction{
        return ExchangeFilterFunction.ofRequestProcessor { clientRequest: ClientRequest ->
            println("Request: ${clientRequest.method()}  ${clientRequest.url()}")
            clientRequest.headers()
                .forEach { name: String?, values: List<String?> ->
                    values.forEach(
                        Consumer { value: String? ->
                            println(
                                "${name}=${value}"
                            )
                        })
                }
            println(clientRequest.body().toString())
            Mono.just(clientRequest)
        }
    }

    private fun logResponse(): ExchangeFilterFunction {
        return ExchangeFilterFunction.ofResponseProcessor { clientResponse: ClientResponse ->
            println("Response status: ${clientResponse.statusCode()}")
            clientResponse.headers().asHttpHeaders()
                .forEach { name: String?, values: List<String?> ->
                    values.forEach(
                        Consumer { value: String? ->
                            println(
                                "${name}=${value}"
                            )
                        })
                }
            Mono.just(clientResponse)
        }
    }
}