package com.lukasz.wolski.DatingAppBackend.services

import com.lukasz.wolski.DatingAppBackend.model.*
import com.lukasz.wolski.DatingAppBackend.repositories.InterestedGenderRepository
import org.springframework.stereotype.Service

@Service
class InterestedGenderService(private val interestedGenderRepository: InterestedGenderRepository) {
    fun genderPreferencesExists(profile: ProfileModel, gender: DictionaryGenderModel): List<InterestedGenderModel> {
        return this.interestedGenderRepository.findInterestedGenderModelByProfileAndGender(profile,gender)
    }
    fun save(interestedGender: InterestedGenderModel): InterestedGenderModel {
        return this.interestedGenderRepository.save(interestedGender)
    }
    fun getInterestedGenderByProfileId(idProfile: ProfileModel, idGender: DictionaryGenderModel): InterestedGenderModel? {
        return this.interestedGenderRepository.findFirstInterestedGenderModelByProfileAndGender(idProfile, idGender)
    }
    fun getAllInterestedGenderByProfile(profile: ProfileModel): ArrayList<InterestedGenderModel>? {
        return this.interestedGenderRepository.findAllInterestedGenderModelByProfileAndDecisionIs(profile, 1)
    }

}