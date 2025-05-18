package kfd.cherkasov.lab4.services

import kfd.cherkasov.lab4.database.entities.Transaction
import kfd.cherkasov.lab4.database.repositories.CurrencyDao
import kfd.cherkasov.lab4.database.repositories.TransactionDao
import kfd.cherkasov.lab4.database.repositories.WalletDao
import kfd.cherkasov.lab4.mappers.TransactionMapper
import kfd.cherkasov.lab4.models.requests.OperationRequest
import kfd.cherkasov.lab4.models.responses.TransactionDataResponse
import kfd.cherkasov.lab4.models.responses.WalletDataResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.math.min
import kotlin.random.Random.Default.nextDouble

@Service
class OperationService (
    val currencyService: CurrencyService,
    val walletService: WalletService,
    val pairService: PairService,
    val walletDao: WalletDao,
    val transactionDao: TransactionDao,
    val currencyDao: CurrencyDao,
    val transactionMapper: TransactionMapper
) {

    // проверяю, существует ли валютная пара. выглядит убого, но пойдет
    fun pairCheck(firstCurrencyId: Int, secondCurrencyId: Int): Boolean {
        try {pairService.getPairIdByCurrenciesId(firstCurrencyId, secondCurrencyId)}
        catch (e: IndexOutOfBoundsException) {
            try {pairService.getPairIdByCurrenciesId(secondCurrencyId, firstCurrencyId)}
            catch (e: IndexOutOfBoundsException) {return false}
        }
        return true
    }
    @Transactional
    fun proceedDeal(userId: Long, data: OperationRequest): TransactionDataResponse {
        // сразу нахожу энтити валют, кошельков т.к. в будущем буду обновлять их курсы через CrudRepository
        val currencyToBuy = currencyService.findCurrencyByShortname(data.currencyToBuyCode)
        val currencyToSell = currencyService.findCurrencyByShortname(data.currencyToSellCode)
        // если пара существует
        if (pairCheck(currencyToSell.id, currencyToBuy.id)) {
            val walletToDeposit = walletService.getWalletByUserAndCurrency(userId, currencyToBuy.id)
            val walletToWithdraw = walletService.getWalletByUserAndCurrency(userId, currencyToSell.id)
            // нахожу курс в дабл, но он нас не особо интересует, все равно по итогу все вычисления переведутся в лонг
            val course =
                currencyToSell.marketValue.toDouble() / currencyToBuy.marketValue
            // считаем, сколько разрешено купить пользователю. amountToBuy вводится как валюта * 100
            val possibleToBuy: Long =
                min((currencyToBuy.bankAmount), ((course * walletToWithdraw.balance).toLong()))
            if (data.amountToBuy <= possibleToBuy || data.amountToBuy > 0) {
                // если указано все верно, меняю балансы и курс валют
                walletToDeposit.balance += data.amountToBuy
                walletToWithdraw.balance -= (data.amountToBuy.toDouble() / course).toInt()
                currencyToBuy.marketValue = (currencyToBuy.marketValue.toDouble() * (nextDouble() / 20 + 1)).toLong()
                currencyToSell.marketValue = (currencyToSell.marketValue.toDouble() * (nextDouble() / 20 + 1)).toLong()
                // сохранаю транзакцию
                val transaction = Transaction(
                    userId = userId,
                    currencyToBuyId = currencyToBuy.id,
                    currencyToSellId = currencyToSell.id,
                    amount = data.amountToBuy,
                    isSuccessfull = true
                )
                // обновляю всё в бдшке
                walletDao.save(walletToDeposit)
                walletDao.save(walletToWithdraw)
                currencyDao.save(currencyToSell)
                currencyDao.save(currencyToBuy)
                transactionDao.save(transaction)
                return transactionMapper.entityToResponse(transaction)
            }
        }
        // если ничего не вышло (либо нет пары, либо не нашло валюту) сохраняю такую транзакцию (может быть полезна,
        // т.к. есть айди юзера и время транзакции, можно сделать анти ддос)
        val transaction = Transaction(
            userId = userId,
            currencyToSellId = -1,
            currencyToBuyId = -1,
            amount = data.amountToBuy,
            isSuccessfull = false
        )
        transactionDao.save(transaction)
        return transactionMapper.entityToResponse(transaction)
    }

    @Transactional
    fun addBalance(userId: Long, currencyId: Int, amount: Long): WalletDataResponse {
        val wallet = walletService.getWalletByUserAndCurrency(userId, currencyId)
        wallet.balance += amount
        walletDao.save(wallet)
        return walletService.walletMapper.entityToResponse(wallet)

    }
}
