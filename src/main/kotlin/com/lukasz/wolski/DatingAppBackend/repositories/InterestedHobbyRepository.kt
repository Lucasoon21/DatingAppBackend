package com.lukasz.wolski.DatingAppBackend.repositories

import com.lukasz.wolski.DatingAppBackend.model.*
import org.springframework.data.jpa.repository.JpaRepository

interface InterestedHobbyRepository:JpaRepository<InterestedHobbyModel,Int> {

    fun findInterestedHobbyModelByProfileAndHobby(profile:ProfileModel, hobby: TypeHobbyModel): List<InterestedHobbyModel>

    fun findFirstInterestedHobbyModelByProfileAndHobby(profileId: ProfileModel, hobbyId: TypeHobbyModel): InterestedHobbyModel
}