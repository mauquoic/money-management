package com.mauquoi.moneymanagement.moneymanagement.domain.services

import com.mauquoi.moneymanagement.moneymanagement.domain.entities.AccountFixture
import com.mauquoi.moneymanagement.moneymanagement.domain.entities.UserFixture
import com.mauquoi.moneymanagement.moneymanagement.domain.exceptions.AccountNotFoundException
import com.mauquoi.moneymanagement.moneymanagement.domain.repositories.AccountRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*

@ExtendWith(MockKExtension::class)
internal class AccountServiceTest {

    @MockK
    lateinit var accountRepository: AccountRepository

    @MockK
    lateinit var userService: UserService

    @InjectMockKs
    lateinit var accountService: AccountService

    @Test
    fun getAccount() {
        val id = UUID.randomUUID()
        every { accountRepository.findById(any()) } returns Optional.of(AccountFixture.account(id = id))
        accountService.getAccount(id)

        assertAll(
            { verify(exactly = 1) { accountRepository.findById(id) } }
        )
    }

    @Test
    fun getAccount_notFound_exceptionIsThrown() {
        every { accountRepository.findById(any()) } returns Optional.empty()
        assertThrows<AccountNotFoundException> { accountService.getAccount(UUID.randomUUID()) }
    }

    @Test
    internal fun createAccount() {
        val account = AccountFixture.account()
        every { userService.getLoggedInUser() } returns UserFixture.user()
        every { accountRepository.save(any()) } returns account
        accountService.createAccount(account)

        assertAll(
            { verify(exactly = 1) { accountRepository.save(account) } }
        )
    }
}