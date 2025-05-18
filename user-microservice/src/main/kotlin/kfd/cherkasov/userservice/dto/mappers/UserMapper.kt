package kfd.cherkasov.userservice.dto.mappers

import kfd.cherkasov.userservice.database.entities.User
import kfd.cherkasov.userservice.dto.response.UserResponse
import org.springframework.stereotype.Component

@Component
class UserMapper {
    fun mapEntityToResponse(entity: User): UserResponse {
        return UserResponse(
            id = entity.id,
            login = entity.login,
            createdAt = entity.createdAt
        )
    }
}