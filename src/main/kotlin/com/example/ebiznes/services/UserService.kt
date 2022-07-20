package com.example.ebiznes.services

import com.example.ebiznes.models.Users
import com.example.ebiznes.repositories.UsersRepository
import io.jsonwebtoken.Jwts
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(private val usersRepository: UsersRepository) {

    fun save(user: Users): Users {
        return this.usersRepository.save(user)
    }

    fun findByEmail(email: String): Users? {
        return this.usersRepository.findByEmail(email)
    }

    fun checkPassword(password: String, givenPassword: String): Boolean{
        return BCryptPasswordEncoder().matches(givenPassword,password)
    }

    fun authenticate(jwt: String): Users? {
        return try{
            val encodeJWT = Jwts.parser().setSigningKey("secret").parseClaimsJws(jwt).body
            this.usersRepository.findById(encodeJWT.issuer.toInt()).get()
        }catch (e: Exception){
            println(e)
            null
        }
    }
}
