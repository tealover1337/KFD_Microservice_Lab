package kfd.cherkasov.lab4.database.entities

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "wallets")
class Wallet(
    @Column(name = "currency_id", nullable = false)
    val currencyId: Int,

    @Column(name = "owner_id", nullable = false)
    val ownerId: Long,

) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    val createdAt: LocalDateTime = LocalDateTime.now()

    @Column(name = "balance", nullable = false)
    var balance: Long = 0L

}