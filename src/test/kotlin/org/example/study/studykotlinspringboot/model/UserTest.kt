package org.example.study.studykotlinspringboot.model

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class UserTest {

    @Test
    fun `test User entity default values`() {
        // Arrange & Act
        val user = User()

        // Assert
        assertNull(user.id, "User ID should be null by default")
        assertEquals("", user.name, "User name should be an empty string by default")
        assertEquals("", user.email, "User email should be an empty string by default")
    }

    @Test
    fun `test User entity with custom name and email`() {
        // Arrange
        val name = "John Doe"
        val email = "john.doe@example.com"

        // Act
        val user = User(name = name, email = email)

        // Assert
        assertNull(user.id, "User ID should be null for a newly created entity")
        assertEquals(name, user.name, "User name should match the provided value")
        assertEquals(email, user.email, "User email should match the provided value")
    }

    @Test
    fun `test User entity with all properties`() {
        // Arrange
        val id: Long = 1
        val name = "Jane Doe"
        val email = "jane.doe@example.com"

        // Act
        val user = User(id = id, name = name, email = email)

        // Assert
        assertEquals(id, user.id, "User ID should match the provided value")
        assertEquals(name, user.name, "User name should match the provided value")
        assertEquals(email, user.email, "User email should match the provided value")
    }
}