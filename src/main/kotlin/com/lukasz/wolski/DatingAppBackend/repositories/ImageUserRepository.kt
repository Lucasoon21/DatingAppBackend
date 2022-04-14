package com.lukasz.wolski.DatingAppBackend.repositories

import com.lukasz.wolski.DatingAppBackend.model.ImageUserModel
import com.lukasz.wolski.DatingAppBackend.model.InterestedAgeModel
import com.lukasz.wolski.DatingAppBackend.model.ProfileModel
import org.springframework.data.jpa.repository.JpaRepository

interface ImageUserRepository: JpaRepository<ImageUserModel, Int> {
    fun findAllByProfile(profile: ProfileModel): List<ImageUserModel>
    fun getAllByProfile(profile: ProfileModel): ImageUserModel
    fun getByIdImgur(idImgur: String): ImageUserModel?
}
