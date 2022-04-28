package com.lukasz.wolski.DatingAppBackend.dtos

import java.util.*

data class ConversationSocketDTO(
    val _id: Any,
    val text: String,
    val createdAt: Date,
    val userSender: Int,
    val user: UserForMessage,
)
