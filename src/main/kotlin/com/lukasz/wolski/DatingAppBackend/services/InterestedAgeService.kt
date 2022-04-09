package com.lukasz.wolski.DatingAppBackend.services

import com.lukasz.wolski.DatingAppBackend.model.InterestedAgeModel
import com.lukasz.wolski.DatingAppBackend.model.ProfileModel
import com.lukasz.wolski.DatingAppBackend.repositories.InterestedAgeRepository
import org.springframework.stereotype.Service

@Service
class InterestedAgeService(private val interestedAgePreferencesrepository: InterestedAgeRepository) {
    fun save(agePreferencesModel: InterestedAgeModel): InterestedAgeModel {
        return this.interestedAgePreferencesrepository.save(agePreferencesModel)
    }
    fun profileExist(profile: ProfileModel): List<InterestedAgeModel> {
        return this.interestedAgePreferencesrepository.findInterestedAgeModelByProfileId(profile)
    }
    fun getInterestedAgeByProfileId(idProfile: ProfileModel): InterestedAgeModel? {
        return this.interestedAgePreferencesrepository.findFirstInterestedAgeModelByProfileId(idProfile)
    }
}
