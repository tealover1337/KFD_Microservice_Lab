package kfd.cherkasov.lab4.controllers

import kfd.cherkasov.lab4.models.requests.AddCurrencyRequest
import kfd.cherkasov.lab4.models.requests.UpdateCurrencyRequest
import kfd.cherkasov.lab4.models.responses.CurrencyDataResponse
import kfd.cherkasov.lab4.services.CurrencyService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/currencies")
class CurrencyController (val currencyService: CurrencyService) {

    @GetMapping
    fun getAllCurrencies(): ResponseEntity<List<CurrencyDataResponse>> = ResponseEntity
        .status(HttpStatus.OK)
        .body(currencyService.getAllCurrencies())

    @GetMapping("/{id}")
    fun getCurrencyByID(@PathVariable("id") id: Int) = ResponseEntity
        .status(HttpStatus.OK)
        .body(currencyService.getCurrencyById(id))

    @PostMapping
    fun addCurrency(@RequestBody request: AddCurrencyRequest) = ResponseEntity
        .status(HttpStatus.CREATED)
        .body(currencyService.addCurrency(request))

    @PutMapping("/{id}")
    fun updateCurrency(@PathVariable("id") id: Int, @RequestBody request: UpdateCurrencyRequest) = ResponseEntity
        .status(HttpStatus.CREATED)
        .body(currencyService.updateCurrency(id, request))

    @DeleteMapping("/{id}")
    fun deleteCurrency(@PathVariable("id") id: Int) = ResponseEntity
        .status(HttpStatus.NO_CONTENT)
        .body(currencyService.deleteCurrencyById(id))
}