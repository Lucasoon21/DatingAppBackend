package com.lukasz.wolski.DatingAppBackend.repositories

import com.lukasz.wolski.DatingAppBackend.model.ChatModel
import com.lukasz.wolski.DatingAppBackend.model.ProfileModel
import org.springframework.data.jpa.repository.JpaRepository

interface ChatRepository: JpaRepository<ChatModel, Int> {
    fun findDistinctByReceiverProfileOrderByDateMessage(profileMy: ProfileModel): List<ChatModel>
    fun findFirstByReceiverProfileAndSenderProfileOrderByDateMessageDesc(profileFirst: ProfileModel, profileSecond: ProfileModel): ChatModel?
    fun findAllByReceiverProfileAndSenderProfileOrSenderProfileAndReceiverProfileOrderByDateMessageDesc(profileMy1: ProfileModel,profileOther1: ProfileModel,profileMy2: ProfileModel,profileOther2: ProfileModel): List<ChatModel>
    fun findAllByReceiverProfileAndSenderProfileOrderByDateMessageDesc(profileFirst: ProfileModel, profileSecond: ProfileModel): List<ChatModel>
}