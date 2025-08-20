package org.example.study.studykotlinspringboot.Service

import org.example.study.studykotlinspringboot.Repository.UserRepository
import org.example.study.studykotlinspringboot.controller.dto.UserCreateRequest
import org.example.study.studykotlinspringboot.controller.dto.UserUpdateRequest
import org.example.study.studykotlinspringboot.model.User
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {
    fun getAllUsers(): List<User> = userRepository.findAll()

    fun getUserById(id: Long): User =
        userRepository.findById(id).orElseThrow { NoSuchElementException("User not found: $id") }

    fun createUser(requestBody: UserCreateRequest): User {
        val newUser = User(name = requestBody.name, email = requestBody.email)
        return userRepository.save(newUser)
    }

    fun updateUser(id: Long, updatedUser: UserUpdateRequest): User {
        val existingUser = getUserById(id)
        val updatedUser = existingUser.copy(
            name = updatedUser.name,
            email = updatedUser.email
        )
        return userRepository.save(updatedUser)
    }

    fun deleteUser(id: Long) {
        if (!userRepository.existsById(id)) {
            throw NoSuchElementException("User not found: $id")
        }
        userRepository.deleteById(id)
    }
}