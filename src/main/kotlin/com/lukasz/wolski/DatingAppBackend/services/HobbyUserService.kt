package com.lukasz.wolski.DatingAppBackend.services

import com.lukasz.wolski.DatingAppBackend.model.HobbyUserModel
import com.lukasz.wolski.DatingAppBackend.model.InterestedHobbyModel
import com.lukasz.wolski.DatingAppBackend.model.ProfileModel
import com.lukasz.wolski.DatingAppBackend.model.TypeHobbyModel
import com.lukasz.wolski.DatingAppBackend.repositories.HobbyUserRepository
import org.springframework.stereotype.Service

@Service
class HobbyUserService(private val hobbyUserRepository: HobbyUserRepository) {
    fun save(interestedHobby: HobbyUserModel): HobbyUserModel {
        return this.hobbyUserRepository.save(interestedHobby)
    }
    fun hobbyInterestedExists(profile: ProfileModel, hobby: TypeHobbyModel): List<HobbyUserModel> {
        return this.hobbyUserRepository.findHobbyUserModelByProfileAndHobby(profile,hobby)
    }
    fun getInterestedHobbyByProfileId(idProfile: ProfileModel, idHobby: TypeHobbyModel): HobbyUserModel {
        return this.hobbyUserRepository.findFirstInterestedHobbyModelByProfileAndHobby(idProfile, idHobby)
    }
}