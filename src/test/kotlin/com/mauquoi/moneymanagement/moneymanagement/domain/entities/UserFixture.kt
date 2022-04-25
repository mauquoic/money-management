package com.mauquoi.moneymanagement.moneymanagement.domain.entities

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken
import com.google.api.client.json.webtoken.JsonWebSignature
import java.util.*

class UserFixture {

    companion object {
        fun user(
            id: UUID = UUID.randomUUID(),
            email: String = "me@mail.com",
            preferences: UserPreferences = preferences(),
            username: String = "username"
        ): User {
            return User(
                id = id,
                email = email,
                preferences = preferences,
                username = username
            )
        }

        fun preferences(
            id: Long = 1L,
            locale: Locale = Locale.UK,
            darkMode: Boolean = true,
            currency: Currency = Currency.getInstance("CHF")
        ): UserPreferences {
            return UserPreferences(
                id = id, locale = locale, darkMode = darkMode, currency = currency
            )
        }

        fun googleToken(
            email: String = "me@mail.com",
            name: String = "Cédric Mauquoi",
            firstName: String = "Cédric",
            lastName: String = "Mauquoi"
        ): GoogleIdToken {
            val payload = GoogleIdToken.Payload()
            payload.email = email
            payload["email"] = email
            payload["name"] = name
            payload["given_name"] = firstName
            payload["family_name"] = lastName
            return GoogleIdToken(JsonWebSignature.Header(), payload, byteArrayOf(), byteArrayOf())
        }
    }
}