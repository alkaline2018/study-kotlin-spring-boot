package org.example.study.studykotlinspringboot.controller

import org.example.study.studykotlinspringboot.Service.UserService
import org.example.study.studykotlinspringboot.model.User
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
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
    fun getUser(@PathVariable id: Long): User? =
        userService.getUserById(id)
}