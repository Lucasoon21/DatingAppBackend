package com.lukasz.wolski.DatingAppBackend.dtos

data class SendMessageDTO(
    val senderProfileId: Int,
    val receiverProfileId: Int,
    val contentMessage: String,
)
