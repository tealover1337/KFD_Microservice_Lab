package kfd.cherkasov.userservice.database.entity

import jakarta.persistence.*
import kfd.cherkasov.userservice.database.entities.User
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "refresh_token")
class RefreshTokenEntity(
    @Column(name = "token", length = 512)
    val token: String,

    @Column(name = "expires_at")
    val expiresAt: Date,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    @Column(nullable = false, unique = true)
    @CreationTimestamp
    val createdAt: LocalDateTime = LocalDateTime.now()
}
