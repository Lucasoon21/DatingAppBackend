package com.lukasz.wolski.DatingAppBackend.dtos

import com.lukasz.wolski.DatingAppBackend.model.ImageUserModel

data class UserForMessage(
    val _id: Int,
    val name: String,
    val avatar: String,
)
