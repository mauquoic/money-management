package com.mauquoi.moneymanagement.moneymanagement.domain.entities

import java.time.LocalDate
import java.util.*

class AccountFixture {

    companion object {
        fun account(
            id: UUID = UUID.randomUUID(),
            name: String = "name",
            description: String = "description",
            currency: Currency = Currency.getInstance("USD"),
            balance: Double = 1000.0,
            addedOn: LocalDate = LocalDate.now(),
            user: User = UserFixture.user()
        ): Account {
            return Account(id = id, name = name, description = description, currency = currency, balance = balance, addedOn = addedOn, user = user)
        }
    }
}