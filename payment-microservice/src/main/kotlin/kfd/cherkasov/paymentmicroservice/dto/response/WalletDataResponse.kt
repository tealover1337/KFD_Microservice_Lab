package kfd.cherkasov.lab4.models.responses

import java.time.LocalDateTime

data class WalletDataResponse(
    val ownerId: Long,
    val currencyId: Int,
    val balance: Long,
    val createdAt: LocalDateTime
)