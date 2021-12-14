package com.lukasz.wolski.DatingAppBackend.repositories

import com.lukasz.wolski.DatingAppBackend.model.*
import org.springframework.data.jpa.repository.JpaRepository

interface InterestedGenderRepository:JpaRepository<InterestedGenderModel, Int> {

    fun findInterestedGenderModelByProfileAndGender(profile: ProfileModel, gender: TypeGenderModel): List<InterestedGenderModel>
    fun findFirstInterestedGenderModelByProfileAndGender(profileId: ProfileModel, genderId: TypeGenderModel): InterestedGenderModel
}