package com.lukasz.wolski.DatingAppBackend.dtos

import com.lukasz.wolski.DatingAppBackend.model.ImageUserModel

data class ShortProfileUsersOnSwipeDTO(
    val profileId: Int,
    val name: String,
    val age: Int,
    val job: String?,
    val image: ImageUserModel?
)
