package kfd.cherkasov.lab4.database.entities

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "currency_pairs")
class CurrencyPair(
    @Column(name = "name", nullable = false)
    val name: String,

    @Column(name =  "first_currency_id", nullable = false)
    val firstCurrencyId: Int,

    @Column(name = "second_currency_id", nullable = false)
    val secondCurrencyId: Int,

) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    val createdAt: LocalDateTime = LocalDateTime.now()
}