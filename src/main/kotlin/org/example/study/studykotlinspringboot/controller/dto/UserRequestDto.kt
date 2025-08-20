package org.example.study.studykotlinspringboot.controller.dto

data class UserCreateRequest(
    val name: String,
    val email: String
)
data class UserUpdateRequest(
    val name: String,
    val email: String
)