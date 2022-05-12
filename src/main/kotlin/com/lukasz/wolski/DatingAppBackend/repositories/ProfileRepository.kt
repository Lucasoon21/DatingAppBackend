package com.lukasz.wolski.DatingAppBackend.repositories

import com.lukasz.wolski.DatingAppBackend.model.DictionaryGenderModel
import com.lukasz.wolski.DatingAppBackend.model.ProfileModel
import com.lukasz.wolski.DatingAppBackend.model.UserModel
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

interface ProfileRepository: JpaRepository<ProfileModel, Int> {
    fun findByUser(user: UserModel): ProfileModel?
    fun getProfileModelById(user: ProfileModel): ProfileModel
    fun getProfileModelByUser(user: UserModel): ProfileModel?
    fun findFirstByUser(user: Int): ProfileModel
    //fun findAllByIdNotAndAndHeightBetween(id: Int, heightFrom:Int, heightTo:Int): List<ProfileModel>?

    fun findAllByIdNotInAndHeightBetweenAndWeightBetweenAndAndDateBirthBetweenAndDictionaryGenderInAndIdIn(
        id: Collection<Int>,
        heightFrom: Int,
        heightTo: Int,
        weightFrom: Int,
        weightTo: Int,
        ageFrom: Date,
        ageTo: Date,
        gender: Collection<DictionaryGenderModel>,
        userTooInterstedHobby: ArrayList<Int>
    ): List<ProfileModel>?
    fun findAllByIdNotInAndHeightBetweenAndWeightBetweenAndAndDateBirthBetweenAndDictionaryGenderIn(
        id: Collection<Int>,
        heightFrom: Int,
        heightTo: Int,
        weightFrom: Int,
        weightTo: Int,
        ageFrom: Date,
        ageTo: Date,
        gender: Collection<DictionaryGenderModel>,
    ): List<ProfileModel>?
}