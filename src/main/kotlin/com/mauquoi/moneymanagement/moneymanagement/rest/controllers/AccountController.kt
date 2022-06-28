package com.mauquoi.moneymanagement.moneymanagement.rest.controllers

import com.mauquoi.moneymanagement.moneymanagement.domain.services.AccountService
import com.mauquoi.moneymanagement.moneymanagement.rest.constants.URL.AccountUrl.ACCOUNTS
import com.mauquoi.moneymanagement.moneymanagement.rest.constants.URL.AccountUrl.ACCOUNT_BY_ID
import com.mauquoi.moneymanagement.moneymanagement.rest.constants.URL.PathVariable.ACCOUNT_ID
import com.mauquoi.moneymanagement.moneymanagement.rest.dto.AccountDto
import com.mauquoi.moneymanagement.moneymanagement.rest.dto.BalanceDto
import com.mauquoi.moneymanagement.moneymanagement.rest.extension.toDomain
import com.mauquoi.moneymanagement.moneymanagement.rest.extension.toDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.inject.Inject


@RestController
@CrossOrigin
class AccountController @Inject constructor(private val accountService: AccountService) {

    @GetMapping(ACCOUNT_BY_ID)
    fun getAccount(@PathVariable(ACCOUNT_ID) accountId: UUID): ResponseEntity<AccountDto> {
        return ResponseEntity.ok(accountService.getAccount(accountId).toDto())
    }

    @GetMapping(ACCOUNTS)
    fun getAccounts(): ResponseEntity<List<AccountDto>> {
        return ResponseEntity.ok(accountService.getAccounts().map { it.toDto() })
    }

    @PutMapping(ACCOUNT_BY_ID)
    fun editAccount(
        @PathVariable(ACCOUNT_ID) accountId: UUID,
        @RequestBody accountDto: AccountDto
    ): ResponseEntity<Nothing> {
        accountService.editAccount(accountId, accountDto.toDomain())
        return ResponseEntity.noContent().build()
    }

    @PostMapping("$ACCOUNT_BY_ID/update")
    fun updateAccount(
        @PathVariable(ACCOUNT_ID) accountId: UUID,
        @RequestBody balanceDto: BalanceDto
    ): ResponseEntity<Nothing> {
        accountService.updateAccount(accountId, balanceDto.balance)
        return ResponseEntity.noContent().build()
    }

    @PostMapping(ACCOUNTS)
    fun createAccount(@RequestBody accountDto: AccountDto): ResponseEntity<AccountDto> {
        val account = accountService.createAccount(accountDto.toDomain())
        return ResponseEntity.status(HttpStatus.CREATED).body(account.toDto())
    }

}
