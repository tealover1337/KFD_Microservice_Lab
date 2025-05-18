package kfd.cherkasov.userservice.controllers

import kfd.cherkasov.userservice.dto.request.RegisterRequest
import kfd.cherkasov.userservice.dto.request.UpdateLoginRequest
import kfd.cherkasov.userservice.service.impl.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController (val userService: UserService) {

    @GetMapping
    fun getAllUsers() = ResponseEntity
        .status(HttpStatus.OK)
        .body(userService.getAllUsers())

    @GetMapping("/{id}")
    fun getUserById(@PathVariable("id") id: Long) = ResponseEntity
        .status(HttpStatus.OK)
        .body(userService.getUserById(id))

    @PostMapping
    fun createUser(@RequestBody request: RegisterRequest) = ResponseEntity
        .status(HttpStatus.CREATED)
        .body(userService.createUser(request))

    @PutMapping("/{id}")
    fun updateLogin(@PathVariable("id") id: Long, @RequestBody request: UpdateLoginRequest) = ResponseEntity
        .status(HttpStatus.CREATED)
        .body(userService.updateLogin(id, request))

    @DeleteMapping("/{id}")
    fun deleteUserById(@PathVariable("id") id: Long) = ResponseEntity
        .status(HttpStatus.NO_CONTENT)
        .body(userService.deleteUser(id))
}