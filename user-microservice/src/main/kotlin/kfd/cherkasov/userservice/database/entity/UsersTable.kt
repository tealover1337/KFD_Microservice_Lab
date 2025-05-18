package kfd.cherkasov.userservice.database.entities

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "users")
class User(
    @Column(name = "password_hash", nullable = false)
    val passwordHash: String,
) {
    @Column(name = "login", nullable = false)
    var login: String = ""

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    val createdAt: LocalDateTime = LocalDateTime.now()

    @ManyToMany(fetch = FetchType.EAGER) // Роли загружаются сразу с пользователем
    @JoinTable(
        name = "user_roles", // Название таблицы для связи
        joinColumns = [JoinColumn(name = "user_id")], // Столбец для связи с UserEntity
        inverseJoinColumns = [JoinColumn(name = "role_id")] // Столбец для связи с RoleEntity
    )
    var roles: MutableSet<UserRole> = mutableSetOf()
}
