package kfd.cherkasov.lab4.mappers

import kfd.cherkasov.lab4.database.entities.Wallet
import kfd.cherkasov.lab4.models.responses.WalletDataResponse
import org.springframework.stereotype.Component

@Component
class WalletMapper {
    fun entityToResponse(entity: Wallet): WalletDataResponse {
        return WalletDataResponse(
            ownerId = entity.ownerId,
            currencyId = entity.currencyId,
            balance = entity.balance,
            createdAt = entity.createdAt
        )
    }
}