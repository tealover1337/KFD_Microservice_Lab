package kfd.cherkasov.lab4.models.requests

data class AddWalletRequest (
    val currencyId: Int,
    val balance: Long
)