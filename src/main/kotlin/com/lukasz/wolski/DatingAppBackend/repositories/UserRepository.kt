package com.lukasz.wolski.DatingAppBackend.repositories

import com.lukasz.wolski.DatingAppBackend.model.UserInfo
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<UserInfo, Int> {
    fun findByEmail(email:String): UserInfo?
}