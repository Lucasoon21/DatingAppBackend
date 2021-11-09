package com.lukasz.wolski.DatingAppBackend.controller

import com.lukasz.wolski.DatingAppBackend.dtos.LoginDTO
import com.lukasz.wolski.DatingAppBackend.dtos.RegisterDTO
import com.lukasz.wolski.DatingAppBackend.model.UserInfo
import com.lukasz.wolski.DatingAppBackend.services.UserService
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("auth")
class AuthController(private val userService: UserService) {

    @PostMapping("register")
    fun register(@RequestBody body: RegisterDTO): ResponseEntity<UserInfo> {
        val user = UserInfo()
        user.email = body.email
        user.password = body.password
        return ResponseEntity.ok(this.userService.save(user))
    }

    @PostMapping("login")
    fun login(@RequestBody body: LoginDTO, response: HttpServletResponse ): ResponseEntity<Any> {
        val user = this.userService.findByEmail(body.email) ?: return ResponseEntity.badRequest().body("Nie znaleziono")

        if (!user.comparePassword(body.password)) {
            return ResponseEntity.badRequest().body("Złe hasło")
        }


        val issuer = user.id.toString()

        val jwt = Jwts.builder()
            .setIssuer(issuer)
            .setExpiration(Date(System.currentTimeMillis()+60*24*1000)) // 1 dzien _*60*24*1000
            .signWith(SignatureAlgorithm.HS512, "sekret").compact()

        val cookie = Cookie("jwt",jwt)
        cookie.isHttpOnly = true
        response.addCookie(cookie)

        return ResponseEntity.ok("sukces")

    }
}