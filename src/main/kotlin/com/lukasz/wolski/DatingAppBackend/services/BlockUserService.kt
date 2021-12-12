package com.lukasz.wolski.DatingAppBackend.services

import com.lukasz.wolski.DatingAppBackend.model.BlockUserModel
import com.lukasz.wolski.DatingAppBackend.model.ProfileModel
import com.lukasz.wolski.DatingAppBackend.repositories.BlockUserRepository
import org.springframework.stereotype.Service

@Service
class BlockUserService(private val blockUserRepository: BlockUserRepository) {
    fun save(blockDecision: BlockUserModel): BlockUserModel? {
        return this.blockUserRepository.save(blockDecision)
    }
    fun blockExists(profile: ProfileModel, profleBLock: ProfileModel): List<BlockUserModel> {
        return this.blockUserRepository.findBlockUserModelByProfileIdAndProfileIdBlock(profile, profleBLock)
    }
}