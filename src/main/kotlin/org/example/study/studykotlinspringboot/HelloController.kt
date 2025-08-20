package org.example.study.studykotlinspringboot

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController {
    @GetMapping("/api/hello")
    fun hello(@RequestParam name: String = "world") =
        mapOf("message" to "Hello, $name")

    @GetMapping("/api/hello2")
    fun hello2(@RequestParam name: String = "world") =
        mapOf("message" to "Hello2, $name")

    @GetMapping("/api/add")
    fun add(@RequestParam number1: Int, @RequestParam number2: Int): Map<String, Any> {
        val number3: Int = number1 + number2
        return mapOf("message" to number3)
    }

    @GetMapping("/")
    fun health(): Map<String, Any> {
        return mapOf("status" to "ok")
    }

}