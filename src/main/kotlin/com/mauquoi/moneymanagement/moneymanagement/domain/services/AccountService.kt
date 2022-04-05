package com.mauquoi.moneymanagement.moneymanagement.domain.services

import com.mauquoi.moneymanagement.moneymanagement.domain.entities.Account
import com.mauquoi.moneymanagement.moneymanagement.domain.exceptions.AccountNotFoundException
import com.mauquoi.moneymanagement.moneymanagement.domain.repositories.AccountRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import javax.inject.Inject

@Service
class AccountService @Inject constructor(
    private val accountRepository: AccountRepository,
    private val userService: UserService
) {

    fun getAccount(accountId: UUID): Account {
        return accountRepository.findById(accountId).orElseThrow { AccountNotFoundException() }
    }

    fun getAccounts(): List<Account> {
        return accountRepository.findAll()
    }

    @Transactional
    fun createAccount(account: Account): Account {
        val user = userService.getLoggedInUser()

        user.addAccount(account)
        return accountRepository.save(account)
    }
}