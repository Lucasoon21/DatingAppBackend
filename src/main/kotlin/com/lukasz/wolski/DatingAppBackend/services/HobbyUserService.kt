package com.lukasz.wolski.DatingAppBackend.services

import com.lukasz.wolski.DatingAppBackend.model.HobbyUserModel
import com.lukasz.wolski.DatingAppBackend.model.ProfileModel
import com.lukasz.wolski.DatingAppBackend.model.DictionaryHobbyModel
import com.lukasz.wolski.DatingAppBackend.repositories.HobbyUserRepository
import org.springframework.stereotype.Service

@Service
class HobbyUserService(private val hobbyUserRepository: HobbyUserRepository) {
    fun save(interestedHobby: HobbyUserModel): HobbyUserModel {
        return this.hobbyUserRepository.save(interestedHobby)
    }
    fun hobbyInterestedExists(profile: ProfileModel, hobby: DictionaryHobbyModel): List<HobbyUserModel> {
        return this.hobbyUserRepository.findHobbyUserModelByProfileAndHobby(profile,hobby)
    }
    //fun getInterestedHobbyByProfileId(idProfile: ProfileModel, idHobby: DictionaryHobbyModel): HobbyUserModel {
        //return this.hobbyUserRepository.findFirstInterestedHobbyModelByProfileAndHobby(idProfile, idHobby)
    //}
    fun getAllHobbyByProfile(profile: ProfileModel):List<HobbyUserModel>? {
        return this.hobbyUserRepository.findAllByProfile(profile)
    }
    fun getHobbyUserByHobbyIdAndProfileId(profile: ProfileModel, hobby: DictionaryHobbyModel): HobbyUserModel? {
        return this.hobbyUserRepository.findAllByProfileAndHobby(profile,hobby)
    }
}