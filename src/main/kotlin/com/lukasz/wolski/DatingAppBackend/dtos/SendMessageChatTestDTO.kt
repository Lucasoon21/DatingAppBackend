package com.lukasz.wolski.DatingAppBackend.dtos

data class SendMessageChatTestDTO(
    val senderProfileId: Int,
    val receiverProfileId: Int,
    val contentMessage: String,
    val date: String,
    val status: Status,

)
