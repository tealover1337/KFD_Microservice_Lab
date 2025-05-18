package kfd.cherkasov.userservice.service

import kfd.cherkasov.userservice.database.entities.User
import java.util.*

interface JwtTokenService {
    fun getUsernameFromToken(token: String): String
    fun validateToken(token: String): Boolean
    fun createTokenPair(user: User): Pair<String, String>
    fun isTokenNotExpired(token: String): Boolean
    fun getTokenExpiration(token: String): Date
}