package kfd.cherkasov.lab4.database.entities

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "currencies")
class Currency(
    @Column(name = "name", nullable = false)
    val name: String,

    @Column(name = "code_name", nullable = false)
    val codeName: String,

) {
    @Column(name = "market_value", nullable = false)
    var marketValue: Long = 0L


    @Column(name = "bank_amount", nullable = false)
    var bankAmount: Long = 0L

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    val createdAt: LocalDateTime = LocalDateTime.now()
}