package com.mauquoi.moneymanagement.moneymanagement.domain.entities

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
    }
}