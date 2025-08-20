package org.example.study.studykotlinspringboot.controller

import org.example.study.studykotlinspringboot.Service.UserService
import org.example.study.studykotlinspringboot.controller.dto.UserCreateRequest
import org.example.study.studykotlinspringboot.controller.dto.UserUpdateRequest
import org.example.study.studykotlinspringboot.model.User
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import com.fasterxml.jackson.databind.ObjectMapper

@WebMvcTest(UserController::class)
class UserControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var userService: UserService

    private val objectMapper = ObjectMapper()

    @Test
    fun `should return all users`() {
        val userList = listOf(
            User(1L, "John", "john.doe@example.com"),
            User(2L, "Jane", "jane.smith@example.com")
        )
        Mockito.`when`(userService.getAllUsers()).thenReturn(userList)

        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$").isArray)
            .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
    }

    @Test
    fun `should return user by id`() {
        val user = User(1L, "John", "john.doe@example.com")
        Mockito.`when`(userService.getUserById(1L)).thenReturn(user)

        mockMvc.perform(MockMvcRequestBuilders.get("/users/1"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("John"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("john.doe@example.com"))
    }

    @Test
    fun `should create a new user`() {
        val userCreateRequest = UserCreateRequest("John", "john.doe@example.com")
        val returnedUser = User(1L, "John", "john.doe@example.com")
        Mockito.`when`(userService.createUser(userCreateRequest)).thenReturn(returnedUser)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userCreateRequest))
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("John"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("john.doe@example.com"))
    }

    @Test
    fun `should update existing user`() {
        val userUpdateRequest = UserUpdateRequest("John Updated", "john.updated@example.com")
        val updatedUser = User(1L, "John Updated", "john.updated@example.com")
        Mockito.`when`(userService.updateUser(1L, userUpdateRequest)).thenReturn(updatedUser)

        mockMvc.perform(
            MockMvcRequestBuilders.put("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userUpdateRequest))
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("John Updated"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("john.updated@example.com"))
    }

    @Test
    fun `should delete an existing user`() {
        Mockito.doNothing().`when`(userService).deleteUser(1L)

        mockMvc.perform(MockMvcRequestBuilders.delete("/users/1"))
            .andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun `should return not found for non-existing user`() {
        Mockito.`when`(userService.getUserById(99L)).thenThrow(NoSuchElementException("User not found: 99"))

        mockMvc.perform(MockMvcRequestBuilders.get("/users/99"))
            .andExpect(MockMvcResultMatchers.status().isNotFound)
    }
}