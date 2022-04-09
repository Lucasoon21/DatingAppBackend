package com.lukasz.wolski.DatingAppBackend.repositories

import com.lukasz.wolski.DatingAppBackend.model.*
import org.springframework.data.jpa.repository.JpaRepository

interface InterestedHobbyRepository:JpaRepository<InterestedHobbyModel,Int> {

    fun findInterestedHobbyModelByProfileAndHobby(profile:ProfileModel, hobby: DictionaryHobbyModel): List<InterestedHobbyModel>

    fun findFirstInterestedHobbyModelByProfileAndHobby(profileId: ProfileModel, hobbyId: DictionaryHobbyModel): InterestedHobbyModel

    fun findAllByProfile(profileId: ProfileModel): List<InterestedHobbyModel>

    fun findAllByProfileAndHobby(profileId: ProfileModel,hobby: DictionaryHobbyModel): InterestedHobbyModel?



}