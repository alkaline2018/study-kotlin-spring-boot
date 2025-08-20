package org.example.study.studykotlinspringboot.Service

import org.example.study.studykotlinspringboot.Repository.UserRepository
import org.example.study.studykotlinspringboot.model.User
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {
    fun getAllUsers(): List<User> = userRepository.findAll()

    fun getUserById(id: Long): User? = userRepository.findById(id)
}