package kfd.cherkasov.userservice.database.repository

import kfd.cherkasov.userservice.database.entities.UserRole
import org.springframework.data.repository.CrudRepository

interface RoleDao: CrudRepository<UserRole, Int> {
    fun findByName(name: String): MutableList<UserRole>
}