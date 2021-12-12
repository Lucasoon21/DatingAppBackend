package com.lukasz.wolski.DatingAppBackend.repositories

import com.lukasz.wolski.DatingAppBackend.model.HobbyUserModel
import com.lukasz.wolski.DatingAppBackend.model.InterestedHobbyModel
import com.lukasz.wolski.DatingAppBackend.model.ProfileModel
import com.lukasz.wolski.DatingAppBackend.model.TypeHobbyModel
import org.springframework.data.jpa.repository.JpaRepository

interface HobbyUserRepository: JpaRepository<HobbyUserModel, Int> {
    fun findHobbyUserModelByProfileAndHobby(profile: ProfileModel, hobby: TypeHobbyModel): List<HobbyUserModel>

}