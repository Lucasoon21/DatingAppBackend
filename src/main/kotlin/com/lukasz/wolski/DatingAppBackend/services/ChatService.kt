package com.lukasz.wolski.DatingAppBackend.services

import com.lukasz.wolski.DatingAppBackend.model.ChatModel
import com.lukasz.wolski.DatingAppBackend.model.ProfileModel
import com.lukasz.wolski.DatingAppBackend.repositories.ChatRepository
import org.springframework.stereotype.Service

@Service
class ChatService(private val chatRepository: ChatRepository) {
    fun save(chatModel: ChatModel): ChatModel {
        return this.chatRepository.save(chatModel)
    }

    fun getAllConversations(profile: ProfileModel): List<ChatModel> {
        return this.chatRepository.findDistinctByReceiverProfileOrderByDateMessage(profile)
    }

    fun getConversationBetweenTwoUsers(profileFirst: ProfileModel, profileSecond: ProfileModel): ChatModel? {
        return this.chatRepository.findFirstByReceiverProfileAndSenderProfileOrderByDateMessageDesc(profileFirst,profileSecond)
    }

    fun getConversation(profileFirst: ProfileModel, profileSecond: ProfileModel): List<ChatModel> {
        return this.chatRepository.findAllByReceiverProfileAndSenderProfileOrSenderProfileAndReceiverProfileOrderByDateMessageDesc(profileFirst,profileSecond,profileFirst,profileSecond)
    }
    fun getConversationn(profileFirst: ProfileModel, profileSecond: ProfileModel): List<ChatModel> {
        return this.chatRepository.findAllByReceiverProfileAndSenderProfileOrderByDateMessageDesc(profileFirst,profileSecond)
    }

}