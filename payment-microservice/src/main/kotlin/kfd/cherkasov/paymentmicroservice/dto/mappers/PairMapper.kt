package kfd.cherkasov.lab4.mappers

import kfd.cherkasov.lab4.database.entities.CurrencyPair
import kfd.cherkasov.lab4.models.responses.PairDataResponse
import org.springframework.stereotype.Component

@Component
class PairMapper {
    fun entityToResponse(entity: CurrencyPair): PairDataResponse {
        return PairDataResponse(
            name = entity.name,
            firstCurrencyId = entity.firstCurrencyId,
            secondCurrencyId = entity.secondCurrencyId,
            createdAt = entity.createdAt
        )
    }
}