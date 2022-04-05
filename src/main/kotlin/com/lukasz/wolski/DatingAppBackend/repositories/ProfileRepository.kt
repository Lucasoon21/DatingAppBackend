package com.lukasz.wolski.DatingAppBackend.repositories

import com.lukasz.wolski.DatingAppBackend.model.ProfileModel
import com.lukasz.wolski.DatingAppBackend.model.UserModel
import org.springframework.data.jpa.repository.JpaRepository

interface ProfileRepository: JpaRepository<ProfileModel, Int> {
    fun findByUser(user: UserModel): ProfileModel?
    fun getProfileModelById(user: ProfileModel): ProfileModel
    fun getProfileModelByUser(user: UserModel): ProfileModel?
    fun findFirstByUser(user: Int): ProfileModel
}