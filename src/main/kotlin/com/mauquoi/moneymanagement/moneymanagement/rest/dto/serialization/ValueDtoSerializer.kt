package com.mauquoi.moneymanagement.moneymanagement.rest.dto.serialization

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import com.mauquoi.moneymanagement.moneymanagement.domain.services.CurrencyService
import com.mauquoi.moneymanagement.moneymanagement.domain.services.UserService
import com.mauquoi.moneymanagement.moneymanagement.rest.dto.ValueDto

class ValueDtoSerializer(
    t: Class<ValueDto>,
    private val userService: UserService,
    private val currencyService: CurrencyService
) : StdSerializer<ValueDto>(t) {

    override fun serialize(dto: ValueDto, jgen: JsonGenerator, sp: SerializerProvider) {
        val preferredCurrency = userService.getLoggedInUser().preferences.currency
        val currencyRate = currencyService.getConversionRate(preferredCurrency)
        jgen.writeStartObject();
        jgen.writeNumberField("value", dto.value * currencyRate);
        jgen.writeStringField("currency", preferredCurrency.currencyCode);
        jgen.writeEndObject();
    }
}