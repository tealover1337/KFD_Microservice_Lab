package kfd.cherkasov.lab4.database.repositories

import kfd.cherkasov.lab4.database.entities.Currency
import org.springframework.data.repository.CrudRepository

interface CurrencyDao : CrudRepository<Currency, Int> {
    fun findCurrencyByCodeName(codeName: String): MutableList<Currency>
    fun findCurrencyById(id: Int): MutableList<Currency>
}