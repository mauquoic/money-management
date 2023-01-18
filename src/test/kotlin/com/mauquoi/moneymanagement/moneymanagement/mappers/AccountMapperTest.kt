package com.mauquoi.moneymanagement.moneymanagement.mappers

import com.mauquoi.moneymanagement.moneymanagement.domain.entities.AccountFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import java.time.LocalDate


internal class AccountMapperTest {

    private val accountMapper = AccountMapperImpl(AccountSnapshotMapperImpl())

    @Test
    fun account_toDto() {
        val account = AccountFixture.account(containsSnapshot = true)

        val accountDto = accountMapper.toDto(account)

        assertAll(
            { assertThat(accountDto.id).isEqualTo(account.id) },
            { assertThat(accountDto.name).isEqualTo(account.name) },
            { assertThat(accountDto.balance).isEqualTo(account.balance) },
            { assertThat(accountDto.description).isEqualTo(account.description) },
            { assertThat(accountDto.addedOn).isEqualTo(account.addedOn) },
            { assertThat(accountDto.currency).isEqualTo(account.currency) },
            { assertThat(accountDto.snapshots).hasSize(1) },
            { assertThat(accountDto.snapshots[0].validFrom).isEqualTo(LocalDate.now()) },
            { assertThat(accountDto.snapshots[0].validTo).isEqualTo(LocalDate.now()) },
            { assertThat(accountDto.snapshots[0].balance).isEqualTo(account.balance) },
        )
    }
}