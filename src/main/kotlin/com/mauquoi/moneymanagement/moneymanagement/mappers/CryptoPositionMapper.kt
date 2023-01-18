package com.mauquoi.moneymanagement.moneymanagement.mappers

import com.mauquoi.moneymanagement.moneymanagement.domain.entities.CryptoAsset
import com.mauquoi.moneymanagement.moneymanagement.domain.entities.CryptoPosition
import com.mauquoi.moneymanagement.moneymanagement.domain.repositories.CryptoAssetRepository
import com.mauquoi.moneymanagement.moneymanagement.mappers.config.CentralConfig
import com.mauquoi.moneymanagement.moneymanagement.rest.dto.CryptoPositionDto
import com.mauquoi.moneymanagement.moneymanagement.rest.dto.ValueDto
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import org.mapstruct.Named
import org.springframework.beans.factory.annotation.Autowired

@Mapper(config = CentralConfig::class, imports = [ArrayList::class], uses = [CryptoPositionSnapshotMapper::class])
abstract class CryptoPositionMapper {

    @Autowired
    private lateinit var cryptoAssetRepository: CryptoAssetRepository

    @Mappings(
        Mapping(target = "assetSymbol", source = "asset.symbol"),
        Mapping(target = "value", source = ".", qualifiedByName = ["getValue"]),
    )
    abstract fun toDto(position: CryptoPosition): CryptoPositionDto

    abstract fun toDtos(positions: List<CryptoPosition>): List<CryptoPositionDto>

    @Mappings(
        Mapping(target = "asset", source = "assetSymbol", qualifiedByName = ["getAsset"]),
        Mapping(target = "positionSnapshots", expression = "java(new ArrayList<>())"),
        Mapping(target = "user", ignore = true),
        Mapping(target = "addedOn", expression = "java(LocalDate.now())"),
        Mapping(target = "editedOn", expression = "java(LocalDate.now())"),
        Mapping(target = "updateBalance", ignore = true),
        Mapping(target = "immutableInfoFromCryptoPosition", ignore = true),
    )
    abstract fun toDomain(cryptoPositionDto: CryptoPositionDto): CryptoPosition

    @Named(value = "getAsset")
    fun getAsset(assetSymbol: String): CryptoAsset? {
        return this.cryptoAssetRepository.findBySymbol(assetSymbol)
    }

    @Named(value = "getValue")
    fun getValue(position: CryptoPosition): ValueDto {
        return ValueDto(position.amount * position.asset.latestPrice)
    }
}
