package com.lukasz.wolski.DatingAppBackend.repositories

import com.lukasz.wolski.DatingAppBackend.model.InterestedAgeModel
import com.lukasz.wolski.DatingAppBackend.model.InterestedHeightModel
import com.lukasz.wolski.DatingAppBackend.model.ProfileModel
import org.springframework.data.jpa.repository.JpaRepository

interface InterestedHeightRepository: JpaRepository<InterestedHeightModel, Int> {
    fun findFirstInterestedHeightModelByProfileId(profileId: ProfileModel): InterestedHeightModel?

}