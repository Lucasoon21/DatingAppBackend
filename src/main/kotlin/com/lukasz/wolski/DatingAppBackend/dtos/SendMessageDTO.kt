package com.lukasz.wolski.DatingAppBackend.dtos

data class SendMessageDTO(
    val senderProfileId: String,
    val receiverProfileId: String,
    val contentMessage: String,
)
