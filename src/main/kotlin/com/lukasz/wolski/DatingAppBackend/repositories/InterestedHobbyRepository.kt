package com.lukasz.wolski.DatingAppBackend.repositories

import com.lukasz.wolski.DatingAppBackend.model.*
import org.springframework.data.jpa.repository.JpaRepository
import java.util.ArrayList

interface InterestedHobbyRepository:JpaRepository<InterestedHobbyModel,Int> {

    fun findInterestedHobbyModelByProfileAndHobby(profile:ProfileModel, hobby: DictionaryHobbyModel): List<InterestedHobbyModel>

    fun findFirstInterestedHobbyModelByProfileAndHobby(profileId: ProfileModel, hobbyId: DictionaryHobbyModel): InterestedHobbyModel

    fun findAllByProfile(profileId: ProfileModel): List<InterestedHobbyModel>

    fun findAllByProfileAndHobby(profileId: ProfileModel,hobby: DictionaryHobbyModel): InterestedHobbyModel?

    fun findAllByHobbyInAndDecison(hobbyInter: ArrayList<DictionaryHobbyModel>, decision: Int): List<InterestedHobbyModel>?

    fun findAllByProfileAndDecison(profile: ProfileModel, decision: Int): List<InterestedHobbyModel>?

}