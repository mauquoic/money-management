package com.mauquoi.moneymanagement.moneymanagement.domain.entities

import org.hibernate.annotations.GenericGenerator
import java.time.LocalDate
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "crypto_asset")
data class CryptoAsset(
    @Id
    val id: String,
    @Column(name = "name", nullable = false) val symbol: String,
    @Column(name = "name", nullable = false) val name: String,
    @OneToMany(
        cascade = [CascadeType.ALL],
        orphanRemoval = true,
        mappedBy = "asset"
    ) val priceHistory: MutableList<CryptoAssetPrice>
)

@Entity
@Table(name = "crypto_asset_price")
data class CryptoAssetPrice(

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    val id: UUID,
    @ManyToOne(fetch = FetchType.LAZY) val asset: CryptoAsset,
    @Column(name = "date", nullable = false) val date: LocalDate,
    @Column(name = "price", nullable = false) val price: Double
)