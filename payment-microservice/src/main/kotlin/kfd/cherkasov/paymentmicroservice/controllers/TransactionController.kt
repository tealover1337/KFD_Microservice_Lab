package kfd.cherkasov.lab4.controllers

import kfd.cherkasov.lab4.models.requests.AddTransactionRequest
import kfd.cherkasov.lab4.services.TransactionService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/transactions")
class TransactionController (
    val transactionService: TransactionService
) {
    @GetMapping
    fun getAllTransactions() = ResponseEntity
        .status(HttpStatus.OK)
        .body(transactionService.getAllTransactions())

    @GetMapping("/{id}")
    fun getTransactionById(@PathVariable("id") id: Long) = ResponseEntity
        .status(HttpStatus.OK)
        .body(transactionService.getTransactionById(id))

    @PostMapping
    fun addTransaction(@RequestHeader("X-User-Id") userId: Long, @RequestBody request: AddTransactionRequest) = ResponseEntity
        .status(HttpStatus.CREATED)
        .body(transactionService.addTransaction(userId, request))

    @DeleteMapping("/{id}")
    fun deleteTransaction(@PathVariable("id") id: Long) = ResponseEntity
        .status(HttpStatus.NO_CONTENT)
        .body(transactionService.deleteTransactionById(id))
}