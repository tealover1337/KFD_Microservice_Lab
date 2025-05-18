package kfd.cherkasov.lab4.services

import kfd.cherkasov.lab4.database.entities.CurrencyPair
import kfd.cherkasov.lab4.database.repositories.PairDao
import kfd.cherkasov.lab4.mappers.PairMapper
import kfd.cherkasov.lab4.models.requests.AddPairRequest
import kfd.cherkasov.lab4.models.responses.PairDataResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PairService (
    val pairDao: PairDao,
    val pairMapper: PairMapper
) {
    fun getAllPairs(): List<PairDataResponse> = pairDao.findAll().map {pairMapper.entityToResponse(it)}

    fun getPairById(id: Int): PairDataResponse = pairMapper.entityToResponse(pairDao.findCurrencyPairById(id)[0])

    @Transactional
    fun addPair(request: AddPairRequest): PairDataResponse {
        val newPair = CurrencyPair(
            name = request.name,
            firstCurrencyId = request.firstCurrencyId,
            secondCurrencyId = request.secondCurrencyId
        )
        pairDao.save(newPair)
        return pairMapper.entityToResponse(newPair)
    }

    fun deletePairById(id: Int): PairDataResponse {
        val deletedPair = pairDao.findCurrencyPairById(id)[0]
        pairDao.delete(deletedPair)
        return pairMapper.entityToResponse(deletedPair)
    }

    fun getPairIdByCurrenciesId(firstId: Int, secondId: Int): Int {
        return pairDao.getCurrencyPairByFirstCurrencyIdAndSecondCurrencyId(firstId, secondId)[0].id
    }
}