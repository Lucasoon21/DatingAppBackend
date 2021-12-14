package com.lukasz.wolski.DatingAppBackend.services

import com.lukasz.wolski.DatingAppBackend.model.ProfileModel
import com.lukasz.wolski.DatingAppBackend.model.UserModel
import com.lukasz.wolski.DatingAppBackend.repositories.ProfileRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class ProfileService(private val profileRepository: ProfileRepository){
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
    /*
    fun findByEmail(user: UserModel): ProfileModel{
        return
    }
    */
}