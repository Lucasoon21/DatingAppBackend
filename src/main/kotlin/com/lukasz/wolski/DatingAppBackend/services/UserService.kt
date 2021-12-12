package com.lukasz.wolski.DatingAppBackend.services

import com.lukasz.wolski.DatingAppBackend.model.UserModel
import com.lukasz.wolski.DatingAppBackend.repositories.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {
    fun emailExists(email: String): Boolean {
        return this.userRepository.findByEmail(email) != null
    //true jesli istnieje
    }

    fun findIdByEmail(email: String): Int {
        val user = this.userRepository.findByEmail(email);
        if (user != null) {
            return user.id
        }
        else {
            return -1
        }
    }

    fun save(user: UserModel): UserModel? {
            return this.userRepository.save(user)
    }

    fun findByEmail(email: String): UserModel? {
        return this.userRepository.findByEmail(email)
    }
    fun getById(id: Int): UserModel? {
        return this.userRepository.getById(id)
    }
    fun userExistById(id: Int): Boolean {
        return this.userRepository.existsById(id)
    }
    fun getUser(email: String): UserModel? {
        return this.userRepository.findByEmail(email)
    }

}