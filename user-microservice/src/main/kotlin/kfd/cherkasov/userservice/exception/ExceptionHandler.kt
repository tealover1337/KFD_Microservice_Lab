package kfd.cherkasov.userservice.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import kfd.cherkasov.userservice.dto.response.ErrorResponse
import kfd.cherkasov.userservice.exception.exceptions.BadAuthRequestException
import kfd.cherkasov.userservice.exception.exceptions.CredentialsMismatchException
import kfd.cherkasov.userservice.exception.exceptions.NotFoundException

@ControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(BadAuthRequestException::class)
    fun handleBadAuthRequest(e: BadAuthRequestException): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse(e.message!!))
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(e: IllegalArgumentException): ResponseEntity<ErrorResponse> =
        ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse(e.message!!))

    @ExceptionHandler(CredentialsMismatchException::class)
    fun handleCredentialsMismatch(e: CredentialsMismatchException): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(ErrorResponse(e.message ?: "Invalid credentials"))
    }

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFound(e: NotFoundException) = ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(ErrorResponse(e.message ?: "Not Found"))

    // Общий обработчик для непредвиденных ошибок
    @ExceptionHandler(Exception::class)
    fun handleGenericException(e: Exception): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ErrorResponse("Internal server error ${e.message}"))
    }
}