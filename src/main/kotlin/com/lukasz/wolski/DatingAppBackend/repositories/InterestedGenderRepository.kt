package com.lukasz.wolski.DatingAppBackend.repositories

import com.lukasz.wolski.DatingAppBackend.model.*
import org.springframework.data.jpa.repository.JpaRepository

interface InterestedGenderRepository:JpaRepository<InterestedGenderModel, Int> {

    fun findInterestedGenderModelByProfileAndGender(profile: ProfileModel, gender: DictionaryGenderModel): List<InterestedGenderModel>
    fun findFirstInterestedGenderModelByProfileAndGender(profileId: ProfileModel, genderId: DictionaryGenderModel): InterestedGenderModel?
    fun findAllInterestedGenderModelByProfileAndDecisionIs(profileId: ProfileModel, decision: Int): ArrayList<InterestedGenderModel>

}