package com.example.christmas

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ChristmasApplication

fun main(args: Array<String>) {
    runApplication<ChristmasApplication>(*args)
}
