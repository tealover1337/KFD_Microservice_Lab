package kfd.cherkasov.lab4.database.repositories

import kfd.cherkasov.lab4.database.entities.Wallet
import org.springframework.data.repository.CrudRepository

interface WalletDao : CrudRepository<Wallet, Long> {
    fun findWalletById(id: Long): MutableList<Wallet>
    fun findWalletByOwnerIdAndCurrencyId(ownerId: Long, currencyId: Int): MutableList<Wallet>
}