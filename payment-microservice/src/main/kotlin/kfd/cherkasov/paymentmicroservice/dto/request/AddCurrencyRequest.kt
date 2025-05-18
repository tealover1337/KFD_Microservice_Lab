package kfd.cherkasov.lab4.models.requests

data class AddCurrencyRequest (
    val name: String,
    val codeName: String,
    val marketValue: Long,
    val bankAmount: Long
)