package kfd.cherkasov.lab4.controllers

import kfd.cherkasov.lab4.models.requests.AddWalletRequest
import kfd.cherkasov.lab4.models.requests.UpdateWalletRequest
import kfd.cherkasov.lab4.services.WalletService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/wallets")
class WalletController(
    val walletService: WalletService,
) {

    @GetMapping
    fun getAllWallets() = ResponseEntity
        .status(HttpStatus.OK)
        .body(walletService.getAllWallets())

    @GetMapping("/{id}")
    fun getWalletById(@PathVariable("id") id: Long) = ResponseEntity
        .status(HttpStatus.OK)
        .body(walletService.getWalletById(id))

    @PostMapping
    fun createWallet(@RequestHeader("X-User-Id") userId: Long, @RequestBody request: AddWalletRequest) = ResponseEntity
        .status(HttpStatus.CREATED)
        .body(walletService.addWallet(userId, request))

    @PutMapping("/{id}")
    fun updateBalance(@PathVariable("id") id: Long, @RequestBody request: UpdateWalletRequest) = ResponseEntity
        .status(HttpStatus.CREATED)
        .body(walletService.updateBalanceById(id, request))

    @DeleteMapping("/{id}")
    fun deleteWallet(@RequestHeader("X-User-Id") userId: Long, @PathVariable("id") id: Long) = ResponseEntity
        .status(HttpStatus.NO_CONTENT)
        .body(walletService.deleteWalletById(userId, id))
}