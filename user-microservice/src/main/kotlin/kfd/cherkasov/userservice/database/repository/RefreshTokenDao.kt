package kfd.cherkasov.userservice.database.repository

import org.springframework.data.repository.CrudRepository
import kfd.cherkasov.userservice.database.entity.RefreshTokenEntity
import kfd.cherkasov.userservice.database.entity.UserEntity

interface RefreshTokenDao : CrudRepository<RefreshTokenEntity, Long> {
    fun deleteByUserId(userId: Long)
    fun findByToken(token: String): RefreshTokenEntity?
    fun getAllByUser(user: UserEntity): List<RefreshTokenEntity>
}