package kfd.cherkasov.lab4.database.repositories

import kfd.cherkasov.lab4.database.entities.Transaction
import org.springframework.data.repository.CrudRepository

interface TransactionDao : CrudRepository<Transaction, Long> {
    fun getTransactionById(id: Long): MutableList<Transaction>
}