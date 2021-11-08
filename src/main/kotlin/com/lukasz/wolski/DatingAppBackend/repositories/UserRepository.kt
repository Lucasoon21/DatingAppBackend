package com.lukasz.wolski.DatingAppBackend.repositories

import com.lukasz.wolski.DatingAppBackend.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, Int> {
}