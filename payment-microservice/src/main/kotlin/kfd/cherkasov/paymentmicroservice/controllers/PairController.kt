package kfd.cherkasov.lab4.controllers

import kfd.cherkasov.lab4.models.requests.AddPairRequest
import kfd.cherkasov.lab4.services.PairService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/pairs")
class PairController(
    val pairService: PairService,
) {
    @GetMapping
    fun getAllCurrencyPairs() = ResponseEntity
        .status(HttpStatus.OK)
        .body(pairService.getAllPairs())

    @GetMapping("/{id}")
    fun getCurrencyPairById(@PathVariable("id") id: Int) = ResponseEntity
        .status(HttpStatus.OK)
        .body(pairService.getPairById(id))

    @PostMapping
    fun addCurrencyPair(@RequestBody request: AddPairRequest) = ResponseEntity
        .status(HttpStatus.CREATED)
        .body(pairService.addPair(request))

    @DeleteMapping("/{id}")
    fun deleteCurrencyPair(@PathVariable("id") id: Int) = ResponseEntity
        .status(HttpStatus.NO_CONTENT)
        .body(pairService.deletePairById(id))
}