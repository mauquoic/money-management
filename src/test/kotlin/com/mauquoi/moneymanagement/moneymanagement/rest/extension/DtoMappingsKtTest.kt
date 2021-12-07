package com.mauquoi.moneymanagement.moneymanagement.rest.extension

import com.mauquoi.moneymanagement.moneymanagement.domain.entities.AccountFixture
import com.mauquoi.moneymanagement.moneymanagement.rest.dto.AccountDtoFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import java.time.LocalDate

internal class DtoMappingsKtTest {

    @Test
    fun account_toDto() {
        val account = AccountFixture.account()

        val accountDto = account.toDto()

        assertAll(
            { assertThat(accountDto.id).isEqualTo(account.id) },
            { assertThat(accountDto.name).isEqualTo(account.name) },
            { assertThat(accountDto.amount).isEqualTo(account.amount) },
            { assertThat(accountDto.description).isEqualTo(account.description) },
            { assertThat(accountDto.addedOn).isEqualTo(account.addedOn) },
            { assertThat(accountDto.currency).isEqualTo(account.currency) },
        )
    }

    @Test
    fun accountDto_toDomain() {
        val accountDto = AccountDtoFixture.accountDto(id = null, addedOn = null)

        val account = accountDto.toDomain()

        assertAll(
            { assertThat(account.id).isNotNull() },
            { assertThat(account.name).isEqualTo(accountDto.name) },
            { assertThat(account.amount).isEqualTo(accountDto.amount) },
            { assertThat(account.description).isEqualTo(accountDto.description) },
            { assertThat(account.addedOn).isEqualTo(LocalDate.now()) },
            { assertThat(account.currency).isEqualTo(accountDto.currency) },
        )
    }
}