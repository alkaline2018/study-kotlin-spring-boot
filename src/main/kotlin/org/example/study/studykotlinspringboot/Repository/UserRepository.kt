package org.example.study.studykotlinspringboot.Repository

import org.example.study.studykotlinspringboot.model.User
import org.springframework.stereotype.Repository

@Repository
class UserRepository {
    private val users = mutableListOf(
        User(1, "홍길동", "hong@test.com"),
        User(2, "김철수", "kim@test.com")
    )

    fun findAll(): List<User> = users

    fun findById(id: Long): User? = users.find { it.id == id }
}