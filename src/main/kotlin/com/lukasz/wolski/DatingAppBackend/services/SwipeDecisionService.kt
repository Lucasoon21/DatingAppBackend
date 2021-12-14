package com.lukasz.wolski.DatingAppBackend.services

import com.lukasz.wolski.DatingAppBackend.model.*
import com.lukasz.wolski.DatingAppBackend.repositories.SwipeRepository
import org.springframework.stereotype.Service

@Service
class SwipeDecisionService(private val swipeRepository: SwipeRepository) {
    fun save(swipeDecision: SwipeDecisionModel): SwipeDecisionModel? {
        return this.swipeRepository.save(swipeDecision)
    }
    fun swipeExists(profile: ProfileModel, profileSwipe: ProfileModel): List<SwipeDecisionModel> {
        return this.swipeRepository.findSwipeDecisionModelByUserGivenAndUserReciever(profile, profileSwipe)
    }
    fun getPair(idProfile: ProfileModel, idProfileSelect: ProfileModel): SwipeDecisionModel {
        return this.swipeRepository.findFirstSwipeDecisionModelByUserGivenAndUserReciever(idProfile, idProfileSelect)
    }

}