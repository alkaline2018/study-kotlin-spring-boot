package org.example.study.studykotlinspringboot

import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class HelloControllerTest(@Autowired val mockMvc: MockMvc) {

    @Test
    fun `GET hello returns 200`() {
        mockMvc.get("/api/hello?name=Kotlin")
            .andExpect { status().isOk }
    }
}