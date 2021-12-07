package com.mauquoi.moneymanagement.moneymanagement.rest.dto

import java.time.LocalDate
import java.util.*


class AccountDtoFixture {

    companion object {
        fun accountDto(
            id: UUID? = null,
            name: String = "name",
            description: String? = null,
            currency: Currency = Currency.getInstance("USD"),
            amount: Double = 1000.0,
            addedOn: LocalDate? = null
        ): AccountDto {
            return AccountDto(id = id, name = name, description = description, currency = currency, amount = amount, addedOn = addedOn)
        }
    }
}