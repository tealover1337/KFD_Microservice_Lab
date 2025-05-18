package kfd.cherkasov.lab4.database.repositories

import kfd.cherkasov.lab4.database.entities.CurrencyPair
import org.springframework.data.repository.CrudRepository

interface PairDao : CrudRepository<CurrencyPair, Int> {
    fun findCurrencyPairById(id: Int): MutableList<CurrencyPair>
    fun getCurrencyPairByFirstCurrencyIdAndSecondCurrencyId(
        firstCurrencyId: Int,
        secondCurrencyId: Int
    ): MutableList<CurrencyPair>
}