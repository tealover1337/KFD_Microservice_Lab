package kfd.cherkasov.lab4.services

import kfd.cherkasov.lab4.database.entities.Currency
import kfd.cherkasov.lab4.database.repositories.CurrencyDao
import kfd.cherkasov.lab4.exceptions.EntityNotFoundException
import kfd.cherkasov.lab4.mappers.CurrencyMapper
import kfd.cherkasov.lab4.models.requests.AddCurrencyRequest
import kfd.cherkasov.lab4.models.requests.UpdateCurrencyRequest
import kfd.cherkasov.lab4.models.responses.CurrencyDataResponse
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CurrencyService(
    val currencyDao: CurrencyDao,
    val currencyMapper: CurrencyMapper
) {
    fun getAllCurrencies(): List<CurrencyDataResponse> = currencyDao.findAll().map { currencyMapper.entityToResponse(it) }

    fun getCurrencyById(id: Int): CurrencyDataResponse = currencyMapper.entityToResponse(currencyDao.findByIdOrNull(id) ?: throw EntityNotFoundException("No such currency"))

    @Transactional
    fun addCurrency(data: AddCurrencyRequest): CurrencyDataResponse {
        val newCurrency = Currency(
            name = data.name,
            codeName = data.codeName)
        newCurrency.marketValue = data.marketValue
        newCurrency.bankAmount = data.bankAmount
        currencyDao.save(newCurrency)
        return currencyMapper.entityToResponse(newCurrency)
    }

    @Transactional
    fun updateCurrency(id: Int, data: UpdateCurrencyRequest): CurrencyDataResponse {
        val updatedCurrency = (currencyDao.findByIdOrNull(id) ?: throw EntityNotFoundException("No such currency")).let {
            it.marketValue = data.newMarketValue
            it.bankAmount = data.newBankAmount
        }
        return currencyMapper.entityToResponse(currencyDao.findByIdOrNull(id) ?: throw EntityNotFoundException("No such currenncy"))
    }

    fun deleteCurrencyById(id: Int): CurrencyDataResponse {
        val deletedCurrency = currencyMapper.entityToResponse(currencyDao.findByIdOrNull(id) ?: throw EntityNotFoundException("No such currency"))
        currencyDao.deleteById(id)
        return deletedCurrency
    }

    fun findCurrencyByShortname(shortname: String): Currency {
        return currencyDao.findCurrencyByCodeName(shortname)[0]
    }
}