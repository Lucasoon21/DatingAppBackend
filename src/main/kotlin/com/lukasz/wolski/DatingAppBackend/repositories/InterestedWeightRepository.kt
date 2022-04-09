package com.lukasz.wolski.DatingAppBackend.repositories

import com.lukasz.wolski.DatingAppBackend.model.InterestedAgeModel
import com.lukasz.wolski.DatingAppBackend.model.InterestedWeightModel
import com.lukasz.wolski.DatingAppBackend.model.ProfileModel
import org.springframework.data.jpa.repository.JpaRepository

interface InterestedWeightRepository: JpaRepository<InterestedWeightModel, Int> {
    fun findFirstInterestedWeightModelByProfileId(profile: ProfileModel): InterestedWeightModel?
}