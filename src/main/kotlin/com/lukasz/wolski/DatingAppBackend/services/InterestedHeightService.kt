package com.lukasz.wolski.DatingAppBackend.services

import com.lukasz.wolski.DatingAppBackend.model.InterestedAgeModel
import com.lukasz.wolski.DatingAppBackend.model.InterestedHeightModel
import com.lukasz.wolski.DatingAppBackend.model.ProfileModel
import com.lukasz.wolski.DatingAppBackend.repositories.InterestedAgeRepository
import com.lukasz.wolski.DatingAppBackend.repositories.InterestedHeightRepository
import org.springframework.stereotype.Service

@Service
class InterestedHeightService(private val interestedHeightRepository: InterestedHeightRepository) {
    fun save(interestedHeightModel: InterestedHeightModel): InterestedHeightModel {
        return this.interestedHeightRepository.save(interestedHeightModel)
    }

    fun getInterestedHeightByProfileId(profile: ProfileModel): InterestedHeightModel? {
        return this.interestedHeightRepository.findFirstInterestedHeightModelByProfileId(profile)

    }
}