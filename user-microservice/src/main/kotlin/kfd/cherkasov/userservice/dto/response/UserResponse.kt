package kfd.cherkasov.userservice.dto.response

import com.fasterxml.jackson.annotation.JsonProperty
import kfd.cherkasov.userservice.database.entities.User
import java.time.LocalDateTime

data class UserResponse(
    val id: Long,
    val login: String,
    @field:JsonProperty("created_at") val createdAt: LocalDateTime,
)
