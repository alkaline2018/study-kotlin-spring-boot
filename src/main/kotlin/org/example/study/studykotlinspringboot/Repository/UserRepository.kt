package org.example.study.studykotlinspringboot.Repository

import org.example.study.studykotlinspringboot.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long>