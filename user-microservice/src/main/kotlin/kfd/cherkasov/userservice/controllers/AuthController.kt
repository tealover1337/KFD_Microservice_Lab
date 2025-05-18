package kfd.cherkasov.userservice.controllers

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import kfd.cherkasov.userservice.dto.request.LoginRequest
import kfd.cherkasov.userservice.dto.request.RefreshRequest
import kfd.cherkasov.userservice.dto.request.RegisterRequest
import kfd.cherkasov.userservice.dto.response.AuthResponse
import kfd.cherkasov.userservice.service.AuthService
import org.springframework.http.HttpStatus

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {
    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<AuthResponse> =
        ResponseEntity.ok(authService.authenticate(request))

    @PostMapping("/register")
    fun register(@RequestBody request: RegisterRequest): ResponseEntity<AuthResponse> = ResponseEntity
        .status(HttpStatus.CREATED)
        .body(authService.register(request))

    @PostMapping("/refresh")
    fun refresh(@RequestBody request: RefreshRequest): ResponseEntity<AuthResponse> =
        ResponseEntity.ok(authService.refresh(request))

    @PostMapping("/logout")
    fun logout(@RequestBody request: RefreshRequest): ResponseEntity<Map<String, String>> {
        authService.logout(request.refreshToken)
        return ResponseEntity.ok(mapOf("status" to "ok"))
    }
}