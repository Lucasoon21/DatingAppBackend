package com.lukasz.wolski.DatingAppBackend.services

import com.lukasz.wolski.DatingAppBackend.model.*
import com.lukasz.wolski.DatingAppBackend.repositories.InterestedHobbyRepository
import org.springframework.stereotype.Service

@Service
class InterestedHobbyService(private val interestedHobbyRepository: InterestedHobbyRepository) {
    fun save(interestedHobby: InterestedHobbyModel): InterestedHobbyModel {
        return this.interestedHobbyRepository.save(interestedHobby)
    }
    fun hobbyInterestedExists(profile: ProfileModel, hobby: DictionaryHobbyModel): List<InterestedHobbyModel> {
        return this.interestedHobbyRepository.findInterestedHobbyModelByProfileAndHobby(profile,hobby)
    }
    fun getInterestedHobbyByProfileId(idProfile: ProfileModel, idHobby: DictionaryHobbyModel): InterestedHobbyModel {
        return this.interestedHobbyRepository.findFirstInterestedHobbyModelByProfileAndHobby(idProfile, idHobby)
    }
}