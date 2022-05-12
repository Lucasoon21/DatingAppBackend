package com.lukasz.wolski.DatingAppBackend.services

import com.lukasz.wolski.DatingAppBackend.model.DictionaryGenderModel
import com.lukasz.wolski.DatingAppBackend.model.ProfileModel
import com.lukasz.wolski.DatingAppBackend.model.SwipeDecisionModel
import com.lukasz.wolski.DatingAppBackend.repositories.ProfileRepository
import com.lukasz.wolski.DatingAppBackend.repositories.SwipeRepository
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.ZoneId
import java.util.*
import kotlin.collections.ArrayList


@Service
class SwipeDecisionService(
    private val swipeRepository: SwipeRepository,
    private val profileRepository: ProfileRepository,
) {
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

    fun getAllProfiles(profile: ProfileModel, allLikesProfiles: ArrayList<Int>, genderPreferencesUser: ArrayList<DictionaryGenderModel>,
                       userTooInterstedHobby: ArrayList<Int>
    ): List<ProfileModel>? {
        val ageFrom = LocalDate.now().minusYears(profile.interestedAge?.age_to?.toLong() ?: 100)
        val ageTo = LocalDate.now().minusYears(profile.interestedAge?.age_from?.toLong() ?: 18)
        val defaultZoneId = ZoneId.systemDefault()
        return this.profileRepository.findAllByIdNotInAndHeightBetweenAndWeightBetweenAndAndDateBirthBetweenAndDictionaryGenderInAndIdIn(
            allLikesProfiles,
            profile.interestedHeight?.height_from ?: 100,
            profile.interestedHeight?.height_to?: 250,
            profile.interestedWeight?.weight_from ?: 30,
            profile.interestedWeight?.weight_to?: 250,
            Date.from(ageFrom.atStartOfDay(defaultZoneId).toInstant()),
            Date.from(ageTo.atStartOfDay(defaultZoneId).toInstant()),
            genderPreferencesUser,
            userTooInterstedHobby,
        )
    }

    fun getAllLikeMe(profile: ProfileModel, myLike: ArrayList<ProfileModel>): List<SwipeDecisionModel>? {

        return this.swipeRepository.findAllByUserReceiverAndDecisionIsAndUserGivenNotIn(profile, 1, myLike)
    }

    fun getAllProfiles(profile: ProfileModel, allLikesProfiles: ArrayList<Int>, genderPreferencesUser: ArrayList<DictionaryGenderModel>): List<ProfileModel>? {
        val ageFrom = LocalDate.now().minusYears(profile.interestedAge?.age_to?.toLong() ?: 100)
        val ageTo = LocalDate.now().minusYears(profile.interestedAge?.age_from?.toLong() ?: 18)
        val defaultZoneId = ZoneId.systemDefault()

        return this.profileRepository.findAllByIdNotInAndHeightBetweenAndWeightBetweenAndAndDateBirthBetweenAndDictionaryGenderIn(
            allLikesProfiles,
            profile.interestedHeight?.height_from ?: 100,
            profile.interestedHeight?.height_to?: 250,
            profile.interestedWeight?.weight_from ?: 30,
            profile.interestedWeight?.weight_to?: 250,
            Date.from(ageFrom.atStartOfDay(defaultZoneId).toInstant()),
            Date.from(ageTo.atStartOfDay(defaultZoneId).toInstant()),
            genderPreferencesUser
            )
    }


}