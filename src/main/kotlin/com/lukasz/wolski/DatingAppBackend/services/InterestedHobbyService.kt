package com.lukasz.wolski.DatingAppBackend.services

import com.lukasz.wolski.DatingAppBackend.model.*
import com.lukasz.wolski.DatingAppBackend.repositories.InterestedHobbyRepository
import org.springframework.stereotype.Service
import java.util.ArrayList

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
    fun getAllInterestedHobbyByProfile(profile: ProfileModel): List<InterestedHobbyModel> {
        return this.interestedHobbyRepository.findAllByProfile(profile)
    }

    fun getPreferencesHobbyByHobbyIdAndProfileId(profile: ProfileModel, hobby: DictionaryHobbyModel): InterestedHobbyModel? {
        return this.interestedHobbyRepository.findAllByProfileAndHobby(profile,hobby)

    }

    fun getAllOnlyInterestedHobbyByProfile(profile: ProfileModel): List<InterestedHobbyModel>? {
        return this.interestedHobbyRepository.findAllByProfileAndDecison(profile, 1)
    }


}