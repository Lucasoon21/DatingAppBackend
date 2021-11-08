package com.lukasz.wolski.DatingAppBackend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
class DatingAppBackendApplication

fun main(args: Array<String>) {
	runApplication<DatingAppBackendApplication>(*args)
}
