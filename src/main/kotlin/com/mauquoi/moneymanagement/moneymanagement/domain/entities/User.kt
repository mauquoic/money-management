package com.mauquoi.moneymanagement.moneymanagement.domain.entities

import com.mauquoi.moneymanagement.moneymanagement.domain.models.UserDetails
import org.hibernate.Hibernate
import org.hibernate.annotations.GenericGenerator
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    var id: UUID? = null,
    @Column(name = "email", nullable = false, unique = true) @NotNull val email: String,
    @OneToOne(cascade = [CascadeType.ALL]) @JoinColumn(name = "preferences_id") val preferences: UserPreferences = UserPreferences(),
    @Column(name = "username") val username: String?,
    @Column(name = "firstName") val firstName: String? = null,
    @Column(name = "lastName") val lastName: String? = null,
    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true, mappedBy = "user")
    val accounts: MutableList<Account> = arrayListOf()
) {

    fun addAccount(account: Account) {
        account.user = this
        this.accounts.add(account)
    }

    fun toUserDetails(): UserDetails {
        return UserDetails(id = id!!, email = email)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as User

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    override fun toString(): String {
        return "User(id=$id, email='$email', username=$username, firstName=$firstName, lastName=$lastName)"
    }


}

@Entity
@Table(name = "preferences")
class UserPreferences(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @Column(name = "language") val locale: Locale = Locale.UK,
    @Column(name = "currency") val currency: Currency = Currency.getInstance("EUR"),
    @Column(name = "darkMode") val darkMode: Boolean = true
)