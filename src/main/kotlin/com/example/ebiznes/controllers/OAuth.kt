package com.example.ebiznes.controllers

import com.example.ebiznes.models.Users
import com.example.ebiznes.services.UserService
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.context.annotation.Bean
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.context.request.RequestContextListener
import java.util.*
import javax.servlet.http.HttpServletRequest


@Controller
class OAuth(private val userService: UserService) {

    @GetMapping("/auth/signin")
    fun user(@AuthenticationPrincipal principal: OAuth2User, request: HttpServletRequest): ResponseEntity<Any?>? {
        println(principal)
        var emailID = principal.getAttribute<String>("email").toString()
        if(emailID.isEmpty()){
            emailID = principal.getAttribute<String>("id").toString()
        }
        var user = this.userService.findByEmail(emailID)
        if(user == null){
            user = Users()
            user.name = principal.name
            user.email = principal.getAttribute<String>("email").toString()
            this.userService.save(user)
        }
        val jwt = Jwts.builder()
            .setIssuer(user.id.toString())
            .setExpiration(Date(System.currentTimeMillis() + 60 * 24 * 1000))
            .signWith(SignatureAlgorithm.HS256, "secret").compact()

        return ResponseEntity.ok("Bearer $jwt")
    }



    @Bean
    fun requestContextListener(): RequestContextListener? {
        return RequestContextListener()
    }

}