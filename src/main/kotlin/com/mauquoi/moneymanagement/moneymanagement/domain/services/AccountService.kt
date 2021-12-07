package com.mauquoi.moneymanagement.moneymanagement.domain.services

import com.mauquoi.moneymanagement.moneymanagement.domain.entities.Account
import com.mauquoi.moneymanagement.moneymanagement.domain.exceptions.AccountNotFoundException
import com.mauquoi.moneymanagement.moneymanagement.domain.repositories.AccountRepository
import org.springframework.stereotype.Service
import java.util.*
import javax.inject.Inject

@Service
class AccountService @Inject constructor(private val accountRepository: AccountRepository) {

    fun getAccount(accountId: UUID): Account {
        return accountRepository.findById(accountId).orElseThrow{ AccountNotFoundException() }
    }

    fun getAccounts(): List<Account> {
        return accountRepository.findAll()
    }

    fun createAccount(account: Account): Account {
        return accountRepository.save(account)
    }
}