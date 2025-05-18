package kfd.cherkasov.lab4.models.requests

data class UpdateCurrencyRequest (
    val newMarketValue: Long,
    val newBankAmount: Long
)