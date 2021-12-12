package com.lukasz.wolski.DatingAppBackend.repositories

import com.lukasz.wolski.DatingAppBackend.model.BlockUserModel
import com.lukasz.wolski.DatingAppBackend.model.ProfileModel
import com.lukasz.wolski.DatingAppBackend.model.TypeGenderModel
import org.springframework.data.jpa.repository.JpaRepository

interface BlockUserRepository : JpaRepository<BlockUserModel, Int> {

    fun findBlockUserModelByProfileIdAndProfileIdBlock(profileId: ProfileModel, profileIdBlock: ProfileModel) : List<BlockUserModel>
}