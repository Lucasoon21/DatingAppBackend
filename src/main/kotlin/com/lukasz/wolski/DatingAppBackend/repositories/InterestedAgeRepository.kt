package com.lukasz.wolski.DatingAppBackend.repositories

import com.lukasz.wolski.DatingAppBackend.model.InterestedAgeModel
import com.lukasz.wolski.DatingAppBackend.model.ProfileModel
import org.springframework.data.jpa.repository.JpaRepository

interface InterestedAgeRepository: JpaRepository<InterestedAgeModel, Int> {
    fun findInterestedAgeModelByProfileId(Profile: ProfileModel): List<InterestedAgeModel>
    fun findFirstByProfileId(profileId: ProfileModel)
    fun findFirstInterestedAgeModelByProfileId(profileId: ProfileModel): InterestedAgeModel?
}