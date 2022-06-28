package com.mauquoi.moneymanagement.moneymanagement.domain.services

import com.mauquoi.moneymanagement.moneymanagement.domain.entities.Account
import com.mauquoi.moneymanagement.moneymanagement.domain.entities.AccountFixture
import com.mauquoi.moneymanagement.moneymanagement.domain.entities.UserFixture
import com.mauquoi.moneymanagement.moneymanagement.domain.exceptions.AccountNotFoundException
import com.mauquoi.moneymanagement.moneymanagement.domain.exceptions.IllegalEntityAccessException
import com.mauquoi.moneymanagement.moneymanagement.domain.repositories.AccountRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.slot
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
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
        val userId = UUID.randomUUID()
        every { accountRepository.findById(any()) } returns Optional.of(
            AccountFixture.account(
                id = id,
                user = UserFixture.user(id = userId)
            )
        )
        every { userService.getLoggedInUserId() } returns userId
        accountService.getAccount(id)

        assertAll(
            { verify(exactly = 1) { accountRepository.findById(id) } }
        )
    }

    @Test
    fun getAccount_accountDoesNotBelongToUser_exceptionIsThrown() {
        val id = UUID.randomUUID()
        every { accountRepository.findById(any()) } returns Optional.of(
            AccountFixture.account(id = id)
        )
        every { userService.getLoggedInUserId() } returns UUID.randomUUID()

        assertThrows<IllegalEntityAccessException> { accountService.getAccount(id) }
    }

    @Test
    fun getAccount_notFound_exceptionIsThrown() {
        every { accountRepository.findById(any()) } returns Optional.empty()
        assertThrows<AccountNotFoundException> { accountService.getAccount(UUID.randomUUID()) }
    }

    @Test
    fun createAccount() {
        val account = AccountFixture.account()
        every { userService.getLoggedInUser() } returns UserFixture.user()
        every { accountRepository.save(any()) } returns account

        accountService.createAccount(account)

        assertAll(
            { verify(exactly = 1) { accountRepository.save(any()) } }
        )
    }

    @Test
    fun editAccount() {
        val capturedAccount = slot<Account>()
        val account = AccountFixture.account()
        val updatedAccount = AccountFixture.account(balance = 2.0)
        every { userService.getLoggedInUserId() } returns account.user!!.id!!
        every { accountRepository.findById(any()) } returns Optional.of(account)
        every { accountRepository.save(capture(capturedAccount)) } returns account
        accountService.editAccount(account.id!!, updatedAccount)

        assertAll(
            { verify(exactly = 1) { accountRepository.save(any()) } },
            { assertThat(capturedAccount.captured.balance).isEqualTo(2.0) },
            { assertThat(capturedAccount.captured.id).isEqualTo(account.id) }
        )
    }

    @Test
    fun updateAccountBalance() {
        val capturedAccount = slot<Account>()
        val account = AccountFixture.account()
        every { userService.getLoggedInUserId() } returns account.user!!.id!!
        every { accountRepository.findById(any()) } returns Optional.of(account)
        every { accountRepository.save(capture(capturedAccount)) } returns account
        accountService.updateAccount(account.id!!, 2.0)

        assertAll(
            { verify(exactly = 1) { accountRepository.save(any()) } },
            { assertThat(capturedAccount.captured.balance).isEqualTo(2.0) },
            { assertThat(capturedAccount.captured.id).isEqualTo(account.id) },
            { assertThat(capturedAccount.captured.accountSnapshots[0].balance).isEqualTo(1000.0) },
            { assertThat(capturedAccount.captured.accountSnapshots.size).isEqualTo(1) }
        )
    }
}