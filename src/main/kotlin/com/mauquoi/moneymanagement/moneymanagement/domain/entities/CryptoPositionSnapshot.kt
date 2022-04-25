package com.mauquoi.moneymanagement.moneymanagement.domain.entities

import com.mauquoi.moneymanagement.moneymanagement.domain.models.ChangeType
import org.hibernate.annotations.GenericGenerator
import java.time.LocalDate
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "crypto_position_snapshot")
data class CryptoPositionSnapshot(
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    val id: UUID? = null,
    @Column(name = "start_amount", nullable = false) val startAmount: Double,
    @Column(name = "end_amount", nullable = false) val endAmount: Double,
    @Column(name = "start_date", nullable = false) val startDate: LocalDate,
    @Column(name = "end_date", nullable = false) val endDate: LocalDate = LocalDate.now(),
    @ManyToOne(fetch = FetchType.LAZY) val cryptoPosition: CryptoPosition,
    @Column(name = "type", nullable = false) @Enumerated(EnumType.STRING) val type: ChangeType = ChangeType.OTHER
)
