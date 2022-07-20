package com.example.ebiznes.controllers

import com.example.ebiznes.dto.LoginDto
import com.example.ebiznes.dto.RegisterDTO
import com.example.ebiznes.models.Users
import com.example.ebiznes.services.UserService
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping("api")
class Auth(private val userService: UserService) {
    @PostMapping("register")
    fun register(@RequestBody body: RegisterDTO): ResponseEntity<Users> {
        val user = Users()
        user.name = body.name
        user.email = body.email
        user.password = body.password

        return ResponseEntity.ok(this.userService.save(user))
    }

    @PostMapping("login")
    fun login(@RequestBody body: LoginDto): ResponseEntity<Any> {
        val user = this.userService.findByEmail(body.email)
            ?: return ResponseEntity.badRequest().body("user with that email not exist")
        if (!userService.checkPassword(user.password, body.password)) {
            return ResponseEntity.badRequest().body("password not correct")
        }
        val jwt = Jwts.builder()
            .setIssuer(user.id.toString())
            .setExpiration(Date(System.currentTimeMillis() + 60 * 24 * 1000))
            .signWith(SignatureAlgorithm.HS256, "secret").compact()

        return ResponseEntity.ok(jwt)
    }

    @GetMapping("user")
    fun user(@RequestHeader("Authorization") bearer: String?): ResponseEntity<Any>? {
        val user = userService.authenticate(bearer.orEmpty())
        return if(user != null){
            ResponseEntity.ok(user)
        }else{
            ResponseEntity.status(401).body("Unauthenticated")
        }
    }
}