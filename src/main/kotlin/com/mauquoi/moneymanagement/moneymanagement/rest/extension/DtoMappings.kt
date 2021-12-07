package com.mauquoi.moneymanagement.moneymanagement.rest.extension

import com.mauquoi.moneymanagement.moneymanagement.domain.entities.Account
import com.mauquoi.moneymanagement.moneymanagement.rest.dto.AccountDto

fun Account.toDto() = AccountDto(
    id = this.id,
    name = this.name,
    amount = this.amount,
    currency = this.currency,
    description = this.description,
    addedOn = this.addedOn
)

fun AccountDto.toDomain() = Account(
    name = this.name,
    amount = this.amount,
    currency = this.currency,
    description = this.description
)