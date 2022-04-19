package com.mauquoi.moneymanagement.moneymanagement.domain.services

import com.mauquoi.moneymanagement.moneymanagement.domain.entities.Account
import com.mauquoi.moneymanagement.moneymanagement.domain.exceptions.AccountNotFoundException
import com.mauquoi.moneymanagement.moneymanagement.domain.exceptions.IllegalEntityAccessException
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
        val account = accountRepository.findById(accountId).orElseThrow { AccountNotFoundException() }
        if (account.user!!.id != userService.getLoggedInUserId()) {
            throw IllegalEntityAccessException()
        }
        return account
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

    @Transactional
    fun updateAccount(accountId: UUID, balance: Double) {
        val account = getAccount(accountId)
        val snapshot = account.createSnapshot()
        account.accountSnapshots.add(snapshot)
        val updatedAccount = account.updateBalance(balance)
        accountRepository.save(updatedAccount)
    }

    @Transactional
    fun editAccount(accountId: UUID, editedAccount: Account) {
        val account = getAccount(accountId)
        val updatedAccount = editedAccount.setImmutableInfoFromAccount(account)
        accountRepository.save(updatedAccount)
    }
}