package com.lukasz.wolski.DatingAppBackend.repositories

import com.lukasz.wolski.DatingAppBackend.model.InterestedHobbyModel
import com.lukasz.wolski.DatingAppBackend.model.ProfileModel
import com.lukasz.wolski.DatingAppBackend.model.TypeHobbyModel
import org.springframework.data.jpa.repository.JpaRepository

interface InterestedHobbyRepository:JpaRepository<InterestedHobbyModel,Int> {

    fun findInterestedHobbyModelByProfileAndHobby(profile:ProfileModel, hobby: TypeHobbyModel): List<InterestedHobbyModel>

}