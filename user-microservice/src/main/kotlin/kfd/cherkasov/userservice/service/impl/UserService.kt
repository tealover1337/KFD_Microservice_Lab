package kfd.cherkasov.userservice.service.impl

import kfd.cherkasov.userservice.database.entities.User
import kfd.cherkasov.userservice.database.repository.RoleDao
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kfd.cherkasov.userservice.database.repository.UserDao
import kfd.cherkasov.userservice.dto.mappers.UserMapper
import kfd.cherkasov.userservice.dto.request.RegisterRequest
import kfd.cherkasov.userservice.dto.request.UpdateLoginRequest
import kfd.cherkasov.userservice.dto.response.UserResponse
import kfd.cherkasov.userservice.exception.exceptions.BadAuthRequestException
import kfd.cherkasov.userservice.exception.exceptions.NotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component


@Service
class UserService(
    private val userDao: UserDao,
    private val userMapper: UserMapper,
    private val roleDao: RoleDao
) {
    fun convert(entity: User) = userMapper.mapEntityToResponse(entity)

    fun getAllUsers(): List<UserResponse?> = userDao.findAll().map { convert(it) }

    fun getUserById(id: Long): UserResponse = convert(userDao.findById(id).orElseThrow { throw NotFoundException("No such user")})

    @Component
    object PasswordEncoder {
        fun getPasswordEncoder() = BCryptPasswordEncoder(10)
    }

    @Transactional
    fun createUser(registerData: RegisterRequest) : UserResponse {
        val encodedPassword = PasswordEncoder.getPasswordEncoder().encode(registerData.password)

        if (userDao.findByLogin(registerData.login) != null) {
            throw BadAuthRequestException("Login already exists")
        }

        val createdUser = User(
            passwordHash = encodedPassword
        )
        createdUser.login = registerData.login
        val defaultRole = roleDao.findByName("ROLE_USER")[0]
        createdUser.roles.add(defaultRole)
        userDao.save(createdUser)

        return convert(createdUser)
    }

    @Transactional
    fun updateLogin(id: Long, request: UpdateLoginRequest): UserResponse {
        val updatedUser = userDao.findById(id).orElseThrow {NotFoundException("No such user")}
        updatedUser.login = request.login
        userDao.save(updatedUser)
        return userMapper.mapEntityToResponse(updatedUser)
    }

    fun deleteUser(id: Long) {
        userDao.findById(id).orElseThrow {NotFoundException("No such user")}
        userDao.deleteById(id)
    }
}