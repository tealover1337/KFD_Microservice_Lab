package kfd.cherkasov.paymentmicroservice.exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionHandler {
    @ExceptionHandler(NoContextException::class)
    fun handleNoContent(e: NoContextException) = ResponseEntity
        .status(HttpStatus.NO_CONTENT)
        .body(e.message)

    @ExceptionHandler(RestrictedUserException::class)
    fun handleRestrictedUser(e: RestrictedUserException) = ResponseEntity
        .status(HttpStatus.FORBIDDEN)
        .body(e.message)
}