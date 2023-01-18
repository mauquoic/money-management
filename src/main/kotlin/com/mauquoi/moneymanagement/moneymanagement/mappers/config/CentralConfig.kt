package com.mauquoi.moneymanagement.moneymanagement.mappers.config

import com.mauquoi.moneymanagement.moneymanagement.domain.entities.CryptoPosition
import com.mauquoi.moneymanagement.moneymanagement.rest.dto.ValueDto
import org.mapstruct.*

@MapperConfig(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.ERROR,
    mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_FROM_CONFIG,
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL,
    collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
)
interface CentralConfig {
}