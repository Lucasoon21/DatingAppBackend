package com.lukasz.wolski.DatingAppBackend.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import com.lukasz.wolski.DatingAppBackend.service.AuthService
import org.springframework.beans.factory.annotation.Autowired

@RestController
public class AuthController {

//    @Autowired
//    val authService: AuthService

    @GetMapping("/")
    public fun hello(): String {
        return "Hello"
    }
}