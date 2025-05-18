package kfd.cherkasov.lab4.services

import kfd.cherkasov.lab4.database.entities.Transaction
import kfd.cherkasov.lab4.database.repositories.TransactionDao
import kfd.cherkasov.lab4.mappers.TransactionMapper
import kfd.cherkasov.lab4.models.requests.AddTransactionRequest
import kfd.cherkasov.lab4.models.responses.TransactionDataResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TransactionService (
    val transactionDao: TransactionDao,
    val transactionMapper: TransactionMapper
) {
    fun getAllTransactions() = transactionDao.findAll().map { transactionMapper.entityToResponse(it) }

    fun getTransactionById(id: Long) = transactionMapper.entityToResponse(transactionDao.getTransactionById(id)[0])

    @Transactional
    fun addTransaction(userId: Long, request: AddTransactionRequest): TransactionDataResponse {
        val newTransaction = Transaction(
            userId = userId,
            currencyToSellId = request.currencyToSellId,
            currencyToBuyId = request.currencyToBuyId,
            amount = request.amount,
            isSuccessfull = request.isSuccessful,
        )
        transactionDao.save(newTransaction)
        return transactionMapper.entityToResponse(newTransaction)
    }

    fun deleteTransactionById(id: Long): TransactionDataResponse {
        val deletedTransaction = transactionDao.getTransactionById(id)[0]
        transactionDao.delete(deletedTransaction)
        return transactionMapper.entityToResponse(deletedTransaction)
    }
}