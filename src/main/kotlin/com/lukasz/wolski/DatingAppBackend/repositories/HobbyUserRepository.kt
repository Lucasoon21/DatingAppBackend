package com.lukasz.wolski.DatingAppBackend.repositories

import com.lukasz.wolski.DatingAppBackend.model.HobbyUserModel
import com.lukasz.wolski.DatingAppBackend.model.ProfileModel
import com.lukasz.wolski.DatingAppBackend.model.DictionaryHobbyModel
import com.lukasz.wolski.DatingAppBackend.model.InterestedHobbyModel
import org.springframework.data.jpa.repository.JpaRepository
import java.util.ArrayList

interface HobbyUserRepository: JpaRepository<HobbyUserModel, Int> {
    fun findHobbyUserModelByProfileAndHobby(profile: ProfileModel, hobby: DictionaryHobbyModel): List<HobbyUserModel>
    //fun findFirstInterestedHobbyModelByProfileAndHobby(profileId: ProfileModel, hobbyId: DictionaryHobbyModel): HobbyUserModel
    fun findAllByProfile(profileId: ProfileModel): List<HobbyUserModel>?
    fun findAllByProfileAndHobby(profile: ProfileModel, hobby: DictionaryHobbyModel): HobbyUserModel?
    fun findAllByHobbyInAndDecison(hobbyInter: ArrayList<DictionaryHobbyModel>, decision: Int): List<HobbyUserModel>?
}