package com.mauquoi.moneymanagement.moneymanagement.rest

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.mauquoi.moneymanagement.moneymanagement.domain.services.CurrencyService
import com.mauquoi.moneymanagement.moneymanagement.domain.services.UserService
import com.mauquoi.moneymanagement.moneymanagement.rest.dto.ValueDto
import com.mauquoi.moneymanagement.moneymanagement.rest.dto.serialization.ValueDtoSerializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

@Configuration
class RestConfiguration {
    @Bean
    @Primary
    fun objectMapper(currencyService: CurrencyService, userService: UserService): ObjectMapper {
        val objectMapper = ObjectMapper().registerKotlinModule()
        objectMapper.registerModule(JavaTimeModule())
        objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false)
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)

        val valueDtoSerializer = ValueDtoSerializer(ValueDto::class.java, userService, currencyService)
        val valueDtoModule = SimpleModule()
        valueDtoModule.addSerializer(ValueDto::class.java, valueDtoSerializer)
        objectMapper.registerModule(valueDtoModule)

        return objectMapper
    }
}