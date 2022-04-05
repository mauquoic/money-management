package com.mauquoi.moneymanagement.moneymanagement.security

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.google.api.client.googleapis.auth.oauth2.GooglePublicKeysManager
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.client.json.webtoken.JsonWebSignature
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@ConditionalOnProperty(value = ["spring.security.type"], havingValue = "mocked")
class MockSecurityConfiguration {

    @Bean
    fun googleTokenVerifier(): GoogleIdTokenVerifier {
        return MockGoogleIdTokenVerifier(GooglePublicKeysManager(NetHttpTransport(), GsonFactory()))
    }

    class MockGoogleIdTokenVerifier(publicKeys: GooglePublicKeysManager? = null) :
        GoogleIdTokenVerifier(publicKeys) {

        override fun verify(idTokenString: String?): GoogleIdToken {
            idTokenString?.let {
                if (it.startsWith("0")) {
                    throw RuntimeException()
                }
            }
            val payload = GoogleIdToken.Payload()
            payload.email = "me@mail.com"
            payload["email"] = "me@mail.com"
            payload["name"] = "Cédric Mauquoi"
            payload["given_name"] = "Cédric"
            payload["family_name"] = "Mauquoi"
            return GoogleIdToken(JsonWebSignature.Header(), payload, byteArrayOf(), byteArrayOf())
        }
    }
}