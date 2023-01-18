package com.mauquoi.moneymanagement.moneymanagement.rest

import com.fasterxml.jackson.databind.ObjectMapper
import com.mauquoi.moneymanagement.moneymanagement.domain.entities.UserFixture
import com.mauquoi.moneymanagement.moneymanagement.domain.services.CurrencyService
import com.mauquoi.moneymanagement.moneymanagement.domain.services.UserService
import com.mauquoi.moneymanagement.moneymanagement.rest.dto.ValueDto
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class RestConfigurationTest {

    @MockkBean
    private lateinit var currencyService: CurrencyService

    @MockkBean
    private lateinit var userService: UserService

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun objectMapper_valueDto_correctlySerialized() {
        every { userService.getLoggedInUser() } returns UserFixture.user()
        every { currencyService.getConversionRate(any()) } returns 2.0
        val valueDto = ValueDto(1.0)

        val serializedValue = objectMapper.writeValueAsString(valueDto)
        assertThat(serializedValue).isEqualTo("{\"value\":2.0,\"currency\":\"CHF\"}")
    }
}