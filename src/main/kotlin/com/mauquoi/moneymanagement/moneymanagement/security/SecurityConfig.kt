package com.mauquoi.moneymanagement.moneymanagement.security

import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.gson.GsonFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SecurityConfig(@Value("\${spring.security.oauth2.google.client-id}") private val clientId: String) {

    @Bean
    fun googleTokenVerifier(): GoogleIdTokenVerifier {
        val transport = NetHttpTransport()
        val jsonFactory: JsonFactory = GsonFactory()
        return GoogleIdTokenVerifier.Builder(
            transport,
            jsonFactory
        )
            .setAudience(listOf(clientId))
            .build()
    }
}