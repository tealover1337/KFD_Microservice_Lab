package kfd.cherkasov.lab4.models.requests

data class AddBalanceRequest(
    val currencyId: Int,
    val amount: Long
)
