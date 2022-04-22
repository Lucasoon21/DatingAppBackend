package com.lukasz.wolski.DatingAppBackend.repositories

import com.lukasz.wolski.DatingAppBackend.model.ProfileModel
import com.lukasz.wolski.DatingAppBackend.model.SwipeDecisionModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface SwipeRepository: JpaRepository<SwipeDecisionModel, Int> {
    fun findSwipeDecisionModelByUserGivenAndUserReceiver (profileId: ProfileModel, profileIdSwipe: ProfileModel): List<SwipeDecisionModel>

    fun findFirstSwipeDecisionModelByUserGivenAndUserReceiver(profileId: ProfileModel, profileIdSwipe: ProfileModel) : SwipeDecisionModel

    fun findAllByDecisionAndUserGivenAndUserReceiver(decision: Int, profileGiven: ProfileModel, profileReceiver: ProfileModel) : List<SwipeDecisionModel>

    fun findAllByUserGiven(profileFirst: ProfileModel) : ArrayList<SwipeDecisionModel>

//    fun findAllByUserReceiverAndUserGivenNotInAndDecision(profileFirst: ProfileModel,likedMe: ArrayList<ProfileModel>, decision: Int ): List<SwipeDecisionModel>?

    fun findAllByUserReceiverAndDecisionIsAndUserGivenNotIn(profileFirst: ProfileModel,decision: Int, likedMe: Collection<ProfileModel> ): List<SwipeDecisionModel>?

   // @Query("FROM swipe_decision WHERE user_id_given = :profileId", nativeQuery=true)
  //  fun znajdzWszystkie(@Param("profileId") profileId: Int): List<SwipeDecisionModel>

    // @Query("SELECT s FROM swipe_decision s ", nativeQuery=true)
    // fun findAll(profileId: Int): List<SwipeDecisionModel>


}