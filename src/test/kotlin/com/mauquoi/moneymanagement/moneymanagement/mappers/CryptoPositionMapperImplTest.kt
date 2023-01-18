package com.mauquoi.moneymanagement.moneymanagement.mappers

import com.mauquoi.moneymanagement.moneymanagement.domain.entities.CryptoAssetFixture
import com.mauquoi.moneymanagement.moneymanagement.domain.entities.CryptoPosition
import com.mauquoi.moneymanagement.moneymanagement.domain.entities.CryptoPositionFixture
import com.mauquoi.moneymanagement.moneymanagement.domain.repositories.CryptoAssetRepository
import com.mauquoi.moneymanagement.moneymanagement.rest.dto.CryptoPositionDtoFixture
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.time.LocalDate
import java.util.*

@SpringBootTest
@ActiveProfiles("test")
internal class CryptoPositionMapperImplTest {

    @MockkBean
    private lateinit var cryptoAssetRepository: CryptoAssetRepository

    @Autowired
    private lateinit var cryptoPositionMapper: CryptoPositionMapper

    @Test
    fun cryptoPositionSnapshot_toDto() {
        val id = UUID.randomUUID()
        val position: CryptoPosition = CryptoPositionFixture.position(id = id)
        val dto = cryptoPositionMapper.toDto(position)

        assertAll(
            { assertThat(dto.id).isEqualTo(id) },
            { assertThat(dto.name).isEqualTo("name") },
            { assertThat(dto.description).isEqualTo("description") },
            { assertThat(dto.amount).isEqualTo(1000.0) },
            { assertThat(dto.addedOn).isEqualTo(LocalDate.now()) },
            { assertThat(dto.editedOn).isEqualTo(LocalDate.now()) },
            { assertThat(dto.positionSnapshots).hasSize(1) })
    }

    @Test
    fun cryptoPositionDto_toDomain() {
        every { cryptoAssetRepository.findBySymbol(any()) } returns CryptoAssetFixture.cryptoAsset()
        val positionDto = CryptoPositionDtoFixture.cryptoPositionDto(description = "Description")
        val position = cryptoPositionMapper.toDomain(positionDto)

        assertAll(
            { assertThat(position.name).isEqualTo("name") },
            { assertThat(position.description).isEqualTo("Description") },
            { assertThat(position.amount).isEqualTo(1000.0) },
            { assertThat(position.addedOn).isEqualTo(LocalDate.now()) },
            { assertThat(position.editedOn).isEqualTo(LocalDate.now()) },
            { assertThat(position.positionSnapshots).isEmpty() }
        )
    }
}