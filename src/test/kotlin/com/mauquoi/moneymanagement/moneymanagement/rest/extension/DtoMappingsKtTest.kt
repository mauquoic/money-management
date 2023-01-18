package com.mauquoi.moneymanagement.moneymanagement.rest.extension

import com.mauquoi.moneymanagement.moneymanagement.domain.entities.AccountFixture
import com.mauquoi.moneymanagement.moneymanagement.domain.entities.CryptoPosition
import com.mauquoi.moneymanagement.moneymanagement.domain.entities.CryptoPositionFixture
import com.mauquoi.moneymanagement.moneymanagement.rest.dto.AccountDtoFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import java.time.LocalDate
import java.util.*

internal class DtoMappingsKtTest {

    @Test
    fun accountDto_toDomain() {
        val accountDto = AccountDtoFixture.accountDto(id = null, addedOn = null)

        val account = accountDto.toDomain()

        assertAll(
            { assertThat(account.name).isEqualTo(accountDto.name) },
            { assertThat(account.balance).isEqualTo(accountDto.balance) },
            { assertThat(account.description).isEqualTo(accountDto.description) },
            { assertThat(account.addedOn).isEqualTo(LocalDate.now()) },
            { assertThat(account.currency).isEqualTo(accountDto.currency) },
        )
    }
}