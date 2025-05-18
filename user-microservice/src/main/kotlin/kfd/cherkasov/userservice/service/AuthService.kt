package kfd.cherkasov.userservice.service

import kfd.cherkasov.userservice.dto.request.LoginRequest
import kfd.cherkasov.userservice.dto.request.RefreshRequest
import kfd.cherkasov.userservice.dto.request.RegisterRequest
import kfd.cherkasov.userservice.dto.response.AuthResponse

interface AuthService {
    fun authenticate(request: LoginRequest): AuthResponse
    fun register(request: RegisterRequest): AuthResponse
    fun refresh(request: RefreshRequest): AuthResponse
    fun logout(refreshToken: String)
}