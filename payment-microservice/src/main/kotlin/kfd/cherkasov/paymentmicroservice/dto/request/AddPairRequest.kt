package kfd.cherkasov.lab4.models.requests

data class AddPairRequest (
    val name: String,
    val firstCurrencyId: Int,
    val secondCurrencyId: Int
)