package kfd.cherkasov.userservice.service.impl

import kfd.cherkasov.userservice.database.entities.User
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kfd.cherkasov.userservice.database.entity.RefreshTokenEntity
import kfd.cherkasov.userservice.database.repository.RefreshTokenDao
import kfd.cherkasov.userservice.database.repository.UserDao
import kfd.cherkasov.userservice.dto.request.LoginRequest
import kfd.cherkasov.userservice.dto.request.RefreshRequest
import kfd.cherkasov.userservice.dto.request.RegisterRequest
import kfd.cherkasov.userservice.dto.response.AuthResponse
import kfd.cherkasov.userservice.exception.exceptions.BadAuthRequestException
import kfd.cherkasov.userservice.exception.exceptions.CredentialsMismatchException
import kfd.cherkasov.userservice.exception.exceptions.NotFoundException
import kfd.cherkasov.userservice.service.AuthService
import kfd.cherkasov.userservice.service.JwtTokenService
import kfd.cherkasov.userservice.service.impl.UserService
import java.util.*

@Service
class AuthServiceImpl(
    private val userDao: UserDao,
    private val userService: UserService,
    private val refreshTokenDao: RefreshTokenDao,
    private val jwtTokenService: JwtTokenService,
    private val passwordEncoder: PasswordEncoder
) : AuthService {

    @Transactional
    override fun logout(refreshToken: String) {
        refreshTokenDao.findByToken(refreshToken)?.let { refreshTokenDao.delete(it) }
    }

    @Transactional
    override fun authenticate(request: LoginRequest): AuthResponse {
        val user = getUserFromLoginRequest(request) ?: throw BadAuthRequestException("No user exists.")

        if (!passwordEncoder.matches(request.password, user.passwordHash))
            throw CredentialsMismatchException("Password mismatch")

        return authUser(user)
    }
    @Transactional
    override fun register(request: RegisterRequest): AuthResponse {
        val user = userService.createUser(request)
        return authUser(userDao.findById(user.id).orElseThrow{NotFoundException("Internal server error.")})
    }

    @Transactional
    override fun refresh(request: RefreshRequest): AuthResponse {
        val refreshToken = refreshTokenDao.findByToken(request.refreshToken)
            ?: throw BadAuthRequestException("No provided refresh token exists.")

        if (refreshToken.expiresAt.before(Date())) { // Проверка на истечение срока
            refreshTokenDao.delete(refreshToken)
            throw BadAuthRequestException("Token expired.")
        }

        return authUser(refreshToken.user)
    }

    private fun getUserFromLoginRequest(request: LoginRequest): User? = userDao.findByLogin(request.login)

    private fun authUser(user: User): AuthResponse {
        refreshTokenDao.deleteByUserId(user.id)

        val (accessToken, refreshToken) = jwtTokenService.createTokenPair(user)

        refreshTokenDao.save(
            RefreshTokenEntity(
                refreshToken,
                jwtTokenService.getTokenExpiration(refreshToken),
                user
            )
        )

        return AuthResponse(
            accessToken = accessToken,
            refreshToken = refreshToken,
            userId = user.id
        )
    }
}