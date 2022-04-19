package com.mauquoi.moneymanagement.moneymanagement.domain.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.NOT_FOUND)
data class AccountNotFoundException(override val message: String = "ACCOUNT_NOT_FOUND") : RuntimeException()

@ResponseStatus(code = HttpStatus.NOT_FOUND)
data class UserNotFoundException(override val message: String = "USER_NOT_FOUND") : RuntimeException()

@ResponseStatus(code = HttpStatus.FORBIDDEN)
data class IllegalEntityAccessException(override val message: String = "ILLEGAL_ENTITY_ACCESS") : RuntimeException()