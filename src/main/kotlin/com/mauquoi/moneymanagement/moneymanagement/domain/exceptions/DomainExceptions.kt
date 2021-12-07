package com.mauquoi.moneymanagement.moneymanagement.domain.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.NOT_FOUND)
data class AccountNotFoundException(override val message: String = "ACCOUNT_NOT_FOUND") : RuntimeException()