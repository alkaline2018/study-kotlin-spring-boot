package org.example.study.studykotlinspringboot.controller

import org.example.study.studykotlinspringboot.Service.UserService
import org.example.study.studykotlinspringboot.controller.dto.UserCreateRequest
import org.example.study.studykotlinspringboot.controller.dto.UserUpdateRequest
import org.example.study.studykotlinspringboot.model.User
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService
) {

    @GetMapping
    fun getUsers(): List<User> =
        userService.getAllUsers()

    @GetMapping("/{id}")
    fun getUser(@PathVariable id: Long): User =
        userService.getUserById(id)

    @PostMapping
    fun createUser(@RequestBody requestBody: UserCreateRequest): User =
        userService.createUser(requestBody)

    @PutMapping("/{id}")
    fun updateUser(@PathVariable id: Long, @RequestBody user: UserUpdateRequest): User =
        userService.updateUser(id, user)

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: Long) {
        userService.deleteUser(id)
    }
}