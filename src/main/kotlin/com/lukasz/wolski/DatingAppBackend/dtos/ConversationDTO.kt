package com.lukasz.wolski.DatingAppBackend.dtos

import java.util.*

data class ConversationDTO(
   /* val senderProfile: Int,
    val contentMessage: String,
    val dateMessage: Date,
    val dateFormatMessage: String*/
    val _id: Any,
    val text: String,
    val createdAt: Date,
    val user: UserForMessage,
    )
