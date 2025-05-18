package kfd.cherkasov.userservice.database.repository

import kfd.cherkasov.userservice.database.entities.User
import org.springframework.data.repository.CrudRepository

interface UserDao : CrudRepository<User, Long> {
    fun findByLogin(login: String): User?
    fun getUserById(id: Long): MutableList<User>
}