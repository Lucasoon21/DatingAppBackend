package com.lukasz.wolski.DatingAppBackend.repositories

import com.lukasz.wolski.DatingAppBackend.model.InterestedRelationshipModel
import com.lukasz.wolski.DatingAppBackend.model.ProfileModel
import com.lukasz.wolski.DatingAppBackend.model.SwipeDecisionModel
import com.lukasz.wolski.DatingAppBackend.model.TypeRelationshipModel
import org.springframework.data.jpa.repository.JpaRepository

interface SwipeRepository: JpaRepository<SwipeDecisionModel, Int> {
    fun findSwipeDecisionModelByUserGivenAndUserReciever (profileId: ProfileModel, profileIdSwipe: ProfileModel): List<SwipeDecisionModel>

    fun findFirstSwipeDecisionModelByUserGivenAndUserReciever(profileId: ProfileModel, profileIdSwipe: ProfileModel) : SwipeDecisionModel

}