package com.mauquoi.moneymanagement.moneymanagement.rest.dto

import com.mauquoi.moneymanagement.moneymanagement.domain.entities.User
import com.mauquoi.moneymanagement.moneymanagement.domain.entities.UserPreferences
import java.util.*

class UserDtoFixture {
    companion object {
        fun userDto(
            id: UUID = UUID.randomUUID(),
            email: String = "me@mail.com",
            preferences: PreferencesDto = preferences(),
            username: String = "username"
        ): UserDto {
            return UserDto(
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
        ): PreferencesDto {
            return PreferencesDto(
                locale = locale, darkMode = darkMode, currency = currency
            )
        }
    }
}