package com.lukasz.wolski.DatingAppBackend.services

import com.lukasz.wolski.DatingAppBackend.model.UserInfo
import com.lukasz.wolski.DatingAppBackend.repositories.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {

    fun save(user: UserInfo): UserInfo {
        return this.userRepository.save(user)
    }
    fun findByEmail(email: String): UserInfo? {
        return this.userRepository.findByEmail(email)
    }
}