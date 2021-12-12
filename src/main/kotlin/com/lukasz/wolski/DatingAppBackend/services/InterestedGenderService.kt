package com.lukasz.wolski.DatingAppBackend.services

import com.lukasz.wolski.DatingAppBackend.model.BlockUserModel
import com.lukasz.wolski.DatingAppBackend.model.InterestedGenderModel
import com.lukasz.wolski.DatingAppBackend.model.ProfileModel
import com.lukasz.wolski.DatingAppBackend.model.TypeGenderModel
import com.lukasz.wolski.DatingAppBackend.repositories.InterestedGenderRepository
import org.springframework.stereotype.Service

@Service
class InterestedGenderService(private val interestedGenderRepository: InterestedGenderRepository) {
    fun genderPreferencesExists(profile: ProfileModel, gender: TypeGenderModel): List<InterestedGenderModel> {
        return this.interestedGenderRepository.findInterestedGenderModelByProfileAndGender(profile,gender)
    }
    fun save(interestedGender: InterestedGenderModel): InterestedGenderModel {
        return this.interestedGenderRepository.save(interestedGender)
    }

}