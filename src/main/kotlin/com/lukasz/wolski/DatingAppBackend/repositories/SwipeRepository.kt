package com.lukasz.wolski.DatingAppBackend.repositories

import com.lukasz.wolski.DatingAppBackend.model.ProfileModel
import com.lukasz.wolski.DatingAppBackend.model.SwipeDecisionModel
import org.springframework.data.jpa.repository.JpaRepository

interface SwipeRepository: JpaRepository<SwipeDecisionModel, Int> {
    fun findSwipeDecisionModelByUserGivenAndUserReceiver (profileId: ProfileModel, profileIdSwipe: ProfileModel): List<SwipeDecisionModel>

    fun findFirstSwipeDecisionModelByUserGivenAndUserReceiver(profileId: ProfileModel, profileIdSwipe: ProfileModel) : SwipeDecisionModel

    fun findAllByDecisionAndUserGivenAndUserReceiver(decision: Int, profileGiven: ProfileModel, profileReceiver: ProfileModel) : List<SwipeDecisionModel>

}