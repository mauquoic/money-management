package com.mauquoi.moneymanagement.moneymanagement.rest.extension

import com.mauquoi.moneymanagement.moneymanagement.domain.entities.AccountFixture
import com.mauquoi.moneymanagement.moneymanagement.domain.entities.CryptoPosition
import com.mauquoi.moneymanagement.moneymanagement.domain.entities.CryptoPositionFixture
import com.mauquoi.moneymanagement.moneymanagement.rest.dto.AccountDtoFixture
import com.mauquoi.moneymanagement.moneymanagement.rest.dto.CryptoPositionDtoFixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import java.time.LocalDate
import java.util.*

internal class DtoMappingsKtTest {

    @Test
    fun account_toDto() {
        val account = AccountFixture.account(containsSnapshot = true)

        val accountDto = account.toDto()

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

    @Test
    fun cryptoPositionDto_toDomain() {
        val positionDto = CryptoPositionDtoFixture.cryptoPositionDto(description = "Description")
        val position = positionDto.toDomain()

        assertAll(
            { assertThat(position.name).isEqualTo("name") },
            { assertThat(position.description).isEqualTo("Description") },
            { assertThat(position.amount).isEqualTo(1000.0) },
            { assertThat(position.addedOn).isEqualTo(LocalDate.now()) },
            { assertThat(position.editedOn).isEqualTo(LocalDate.now()) },
            { assertThat(position.positionSnapshots).isEmpty() }
        )
    }

    @Test
    fun cryptoPositionSnapshot_toDto() {
        val id = UUID.randomUUID()
        val position: CryptoPosition = CryptoPositionFixture.position(id = id)
        val dto = position.toDto()

        assertAll(
            { assertThat(dto.id).isEqualTo(id) },
            { assertThat(dto.name).isEqualTo("name") },
            { assertThat(dto.description).isEqualTo("description") },
            { assertThat(dto.amount).isEqualTo(1000.0) },
            { assertThat(dto.addedOn).isEqualTo(LocalDate.now()) },
            { assertThat(dto.editedOn).isEqualTo(LocalDate.now()) },
            { assertThat(dto.positionSnapshots).hasSize(1) })
    }
}