package com.lukasz.wolski.DatingAppBackend.services

import com.lukasz.wolski.DatingAppBackend.model.InterestedAgeModel
import com.lukasz.wolski.DatingAppBackend.model.InterestedHeightModel
import com.lukasz.wolski.DatingAppBackend.model.InterestedWeightModel
import com.lukasz.wolski.DatingAppBackend.model.ProfileModel
import com.lukasz.wolski.DatingAppBackend.repositories.InterestedAgeRepository
import com.lukasz.wolski.DatingAppBackend.repositories.InterestedWeightRepository
import org.springframework.stereotype.Service

@Service
class InterestedWeightService(private val interestedWeightRepository: InterestedWeightRepository) {
    fun save(weightPreferencesModel: InterestedWeightModel): InterestedWeightModel {
        return this.interestedWeightRepository.save(weightPreferencesModel)
    }
    fun getInterestedWeightByProfile(profile: ProfileModel): InterestedWeightModel? {
        return this.interestedWeightRepository.findFirstInterestedWeightModelByProfileId(profile)

    }

}