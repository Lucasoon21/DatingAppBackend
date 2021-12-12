package com.lukasz.wolski.DatingAppBackend.repositories

import com.lukasz.wolski.DatingAppBackend.model.BlockUserModel
import com.lukasz.wolski.DatingAppBackend.model.InterestedGenderModel
import com.lukasz.wolski.DatingAppBackend.model.ProfileModel
import com.lukasz.wolski.DatingAppBackend.model.TypeGenderModel
import org.springframework.data.jpa.repository.JpaRepository

interface InterestedGenderRepository:JpaRepository<InterestedGenderModel, Int> {

    fun findInterestedGenderModelByProfileAndGender(profile: ProfileModel, gender: TypeGenderModel): List<InterestedGenderModel>
}