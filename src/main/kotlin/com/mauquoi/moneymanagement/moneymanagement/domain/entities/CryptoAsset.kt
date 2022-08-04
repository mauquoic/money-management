package com.mauquoi.moneymanagement.moneymanagement.domain.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.GenericGenerator
import java.time.LocalDate
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "crypto_asset")
data class CryptoAsset(
    @Id
    val id: String,
    @Column(name = "symbol", nullable = false) val symbol: String,
    @Column(name = "name", nullable = false) val name: String,
    @Column(name = "marketCapRank", nullable = false) val marketCapRank: Int,
    @Column(name = "latestPrice", nullable = false) val latestPrice: Double,
    @Transient @JsonIgnore var price: Double,
    @OneToMany(
        cascade = [CascadeType.ALL],
        orphanRemoval = true,
        mappedBy = "asset",
        fetch = FetchType.LAZY
    ) val priceHistory: MutableList<CryptoAssetPrice> = mutableListOf()
) {

    fun addNewSnapshot(): CryptoAsset {
        this.priceHistory.add(CryptoAssetPrice(asset = this, price = this.price))
        return this
    }
}

@Entity
@Table(name = "crypto_asset_price")
data class CryptoAssetPrice(

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    val id: UUID? = null,
    @ManyToOne(fetch = FetchType.LAZY) @JsonIgnore val asset: CryptoAsset,
    @Column(name = "date", nullable = false) val date: LocalDate = LocalDate.now(),
    @Column(name = "price", nullable = false) val price: Double
)