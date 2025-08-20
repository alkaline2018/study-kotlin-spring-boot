package org.example.study.studykotlinspringboot.Service

import org.example.study.studykotlinspringboot.Repository.UserRepository
import org.example.study.studykotlinspringboot.controller.dto.UserCreateRequest
import org.example.study.studykotlinspringboot.controller.dto.UserUpdateRequest
import org.example.study.studykotlinspringboot.model.User
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mock
import org.mockito.Mockito.*
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

@SpringBootTest
class UserServiceTest {

    @Autowired
    private lateinit var userService: UserService

    @MockBean
    private lateinit var userRepository: UserRepository

    @Test
    fun `should return all users`() {
        val users = listOf(
            User(id = 1L, name = "John Doe", email = "john.doe@example.com"),
            User(id = 2L, name = "Jane Roe", email = "jane.roe@example.com")
        )

        `when`(userRepository.findAll()).thenReturn(users)

        val result = userService.getAllUsers()

        assertEquals(2, result.size)
        assertEquals("John Doe", result[0].name)
        assertEquals("jane.roe@example.com", result[1].email)
        verify(userRepository, times(1)).findAll()
    }

    @Test
    fun `should return user by id`() {
        val user = User(id = 1L, name = "John Doe", email = "john.doe@example.com")

        `when`(userRepository.findById(1L)).thenReturn(Optional.of(user))

        val result = userService.getUserById(1L)

        assertNotNull(result)
        assertEquals("John Doe", result.name)
        assertEquals("john.doe@example.com", result.email)
        verify(userRepository, times(1)).findById(1L)
    }

    @Test
    fun `should throw exception when user not found by id`() {
        `when`(userRepository.findById(1L)).thenReturn(Optional.empty())

        val exception = assertThrows<NoSuchElementException> {
            userService.getUserById(1)
        }

        assertEquals("User not found: 1", exception.message)
        verify(userRepository, times(1)).findById(1L)
    }

    @Test
    fun `should create a new user`() {
        val request = UserCreateRequest(name = "John Doe", email = "john.doe@example.com")
        val savedUser = User(id = 1L, name = "John Doe", email = "john.doe@example.com")

        `when`(userRepository.save(any(User::class.java))).thenReturn(savedUser)

        val result = userService.createUser(request)

        assertNotNull(result)
        assertEquals("John Doe", result.name)
        assertEquals("john.doe@example.com", result.email)
        verify(userRepository, times(1)).save(any(User::class.java))
    }

    @Test
    fun `should update an existing user`() {
        val existingUser = User(id = 1L, name = "Old Name", email = "old.email@example.com")
        val updateRequest = UserUpdateRequest(name = "New Name", email = "new.email@example.com")
        val updatedUser = existingUser.copy(name = "New Name", email = "new.email@example.com")

        `when`(userRepository.findById(1L)).thenReturn(Optional.of(existingUser))
        `when`(userRepository.save(any(User::class.java))).thenReturn(updatedUser)

        val result = userService.updateUser(1L, updateRequest)

        assertNotNull(result)
        assertEquals("New Name", result.name)
        assertEquals("new.email@example.com", result.email)
        verify(userRepository, times(1)).findById(1L)
        verify(userRepository, times(1)).save(any(User::class.java))
    }

    @Test
    fun `should throw exception when updating non-existent user`() {
        val updateRequest = UserUpdateRequest(name = "New Name", email = "new.email@example.com")

        `when`(userRepository.findById(1L)).thenReturn(Optional.empty())

        val exception = assertThrows<NoSuchElementException> {
            userService.updateUser(1L, updateRequest)
        }

        assertEquals("User not found: 1", exception.message)
        verify(userRepository, times(1)).findById(1L)
    }

    @Test
    fun `should delete a user`() {
        `when`(userRepository.existsById(1L)).thenReturn(true)
        doNothing().`when`(userRepository).deleteById(1L)

        userService.deleteUser(1L)

        verify(userRepository, times(1)).existsById(1L)
        verify(userRepository, times(1)).deleteById(1L)
    }

    @Test
    fun `should throw exception when deleting non-existent user`() {
        `when`(userRepository.existsById(1L)).thenReturn(false)

        val exception = assertThrows<NoSuchElementException> {
            userService.deleteUser(1L)
        }

        assertEquals("User not found: 1", exception.message)
        verify(userRepository, times(1)).existsById(1L)
    }
}