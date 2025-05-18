package kfd.cherkasov.lab4.models.responses

import java.time.LocalDateTime

data class CurrencyDataResponse (
    val name: String,
    val codeName: String,
    val bankAmount: Long,
    val marketValue: Long,
    val createdAt: LocalDateTime
)