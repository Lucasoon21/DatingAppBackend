package com.lukasz.wolski.DatingAppBackend.dtos

import com.lukasz.wolski.DatingAppBackend.model.ImageUserModel
import java.util.*

data class ListConversationsDTO(
    val name:String,
    val profileImageLink: String?,
    val contentLastMessage: String,
    val dateMessage: Date,
    val dateMessageFormat: String,
    val profileId: Int,
)
