package com.lukasz.wolski.DatingAppBackend.dtos

data class SwipeDTO(
    val profileId: Int,
    val selectProfileUserId: Int,
    val decision: Int,
)
