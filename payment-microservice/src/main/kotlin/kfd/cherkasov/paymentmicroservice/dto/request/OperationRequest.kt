package kfd.cherkasov.lab4.models.requests

data class OperationRequest(
    val currencyToBuyCode: String,
    val currencyToSellCode: String,
    val amountToBuy: Long
)
