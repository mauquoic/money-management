package com.mauquoi.moneymanagement.moneymanagement.rest.controllers

import com.mauquoi.moneymanagement.moneymanagement.domain.services.AccountService
import com.mauquoi.moneymanagement.moneymanagement.rest.constants.URL.AccountUrl.ACCOUNTS
import com.mauquoi.moneymanagement.moneymanagement.rest.constants.URL.AccountUrl.GET_ACCOUNT
import com.mauquoi.moneymanagement.moneymanagement.rest.constants.URL.PathVariable.ACCOUNT_ID
import com.mauquoi.moneymanagement.moneymanagement.rest.dto.AccountDto
import com.mauquoi.moneymanagement.moneymanagement.rest.extension.toDomain
import com.mauquoi.moneymanagement.moneymanagement.rest.extension.toDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.inject.Inject


@RestController
@CrossOrigin
class AccountController @Inject constructor(private val accountService: AccountService) {

    @GetMapping(GET_ACCOUNT)
    fun getAccount(@PathVariable(ACCOUNT_ID) accountId: UUID): ResponseEntity<AccountDto> {
        return ResponseEntity.ok(accountService.getAccount(accountId).toDto())
    }

    @GetMapping(ACCOUNTS)
    fun getAccounts(): ResponseEntity<List<AccountDto>> {
        println(SecurityContextHolder.getContext().authentication)
        return ResponseEntity.ok(accountService.getAccounts().map { it.toDto() })
    }

    @PostMapping(ACCOUNTS)
    fun createAccount(@RequestBody accountDto: AccountDto): ResponseEntity<AccountDto> {
        val account = accountService.createAccount(accountDto.toDomain())
        return ResponseEntity.status(HttpStatus.CREATED).body(account.toDto())
    }

}
