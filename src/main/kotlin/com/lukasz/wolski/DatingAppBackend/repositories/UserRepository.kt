package com.lukasz.wolski.DatingAppBackend.repositories

import com.lukasz.wolski.DatingAppBackend.model.UserModel
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<UserModel, Int> {
    fun findByEmail(email:String): UserModel?
    fun getUserModelById(id:Int): UserModel
   // fun existsById(id:Int): Boolean
   // fun findById(id:Int): UserModel?

}