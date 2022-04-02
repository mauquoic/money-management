package com.mauquoi.moneymanagement.moneymanagement.domain.entities

import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

class AccountFixture {

    companion object {
        fun account(
            id: UUID = UUID.randomUUID(),
            name: String = "name",
            description: String = "description",
            currency: Currency = Currency.getInstance("USD"),
            amount: Double = 1000.0,
            addedOn: LocalDate = LocalDate.now()
        ): Account {
            return Account(id = id, name = name, description = description, currency = currency, amount = amount, addedOn = addedOn, user = UserFixture.user())
        }
    }
}