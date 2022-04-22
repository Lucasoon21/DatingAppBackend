package com.lukasz.wolski.DatingAppBackend.services

import com.lukasz.wolski.DatingAppBackend.model.*
import com.lukasz.wolski.DatingAppBackend.repositories.ProfileRepository
import com.lukasz.wolski.DatingAppBackend.repositories.SwipeRepository
import org.springframework.stereotype.Service

@Service
class SwipeDecisionService(private val swipeRepository: SwipeRepository,
                           private val profileRepository: ProfileRepository,) {
    fun save(swipeDecision: SwipeDecisionModel): SwipeDecisionModel? {
        return this.swipeRepository.save(swipeDecision)
    }
    fun swipeExists(profile: ProfileModel, profileSwipe: ProfileModel): List<SwipeDecisionModel> {
        return this.swipeRepository.findSwipeDecisionModelByUserGivenAndUserReceiver(profile, profileSwipe)
    }
    fun getPair(idProfile: ProfileModel, idProfileSelect: ProfileModel): SwipeDecisionModel {
        return this.swipeRepository.findFirstSwipeDecisionModelByUserGivenAndUserReceiver(idProfile, idProfileSelect)
    }
    fun checkMatch(swipeModel: SwipeDecisionModel): Boolean {
        return this.swipeRepository.findAllByDecisionAndUserGivenAndUserReceiver(
            swipeModel.decision,
            swipeModel.userGiven,
            swipeModel.userReceiver
        ).isNotEmpty()
    }

    fun getAllLiked(profile: ProfileModel): ArrayList<SwipeDecisionModel> {
        return this.swipeRepository.findAllByUserGiven(profile)
    }

    fun getAllProfiles(
        profile: ProfileModel,
        allLikesProfiles: ArrayList<Int>,
        genderPreferencesUser: ArrayList<DictionaryGenderModel>
    ): List<ProfileModel>? {

        return this.profileRepository.findAllByIdNotInAndHeightBetweenAndWeightBetweenAndDictionaryGenderIn(
            allLikesProfiles,
            profile.interestedHeight?.height_from ?: 100,
            profile.interestedHeight?.height_to?: 250,
            profile.interestedWeight?.weight_from ?: 30,
            profile.interestedWeight?.weight_to?: 250,
            genderPreferencesUser

        )
        // return this.profileRepository.findAllByIdNotAndAndHeightBetween(profile.id, profile.interestedHeight?.height_from ?: 100, profile.interestedHeight?.height_to?: 250)

    }

    fun getAllLikeMe(profile: ProfileModel, myLike: ArrayList<ProfileModel>): List<SwipeDecisionModel>? {

        return this.swipeRepository.findAllByUserReceiverAndDecisionIsAndUserGivenNotIn(profile, 1, myLike)
    }




}