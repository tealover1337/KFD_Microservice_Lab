package kfd.cherkasov.lab4.models.responses

import java.time.LocalDateTime

data class PairDataResponse (
    val name: String,
    val firstCurrencyId: Int,
    val secondCurrencyId: Int,
    val createdAt: LocalDateTime
)