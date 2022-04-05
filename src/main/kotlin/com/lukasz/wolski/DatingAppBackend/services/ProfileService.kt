package com.lukasz.wolski.DatingAppBackend.services

import com.lukasz.wolski.DatingAppBackend.model.ProfileModel
import com.lukasz.wolski.DatingAppBackend.model.UserModel
import com.lukasz.wolski.DatingAppBackend.repositories.ProfileRepository
import com.lukasz.wolski.DatingAppBackend.repositories.UserRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class ProfileService(private val profileRepository: ProfileRepository, private val userRepository: UserRepository){
    fun save(profile: ProfileModel): ProfileModel? {
        return this.profileRepository.save(profile)
    }

    fun userExist(user: UserModel): Boolean {
        return this.profileRepository.findByUser(user) != null
    }

    fun profileExistById(id: Int): Boolean {
        return this.profileRepository.existsById(id)
    }

    fun getProfileById(id: Int): ProfileModel {
        return this.profileRepository.getById(id)
    }

    fun findById(id: Int): Optional<ProfileModel> {
        return  this.profileRepository.findById(id)
    }
    fun findIdByUser(email: String): Int {
        val userr = this.userRepository.findByEmail(email)
        return profileRepository.findByUser(userr!!)?.id ?: -1

        /*val userr = this.profileRepository.getProfileModelByUser(user)
        if (userr != null) {
            return userr.id
        }
        else {
            return -1
        }*/
    }
    /*
    fun findByEmail(user: UserModel): ProfileModel{
        return
    }
    */
}