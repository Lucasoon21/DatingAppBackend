package com.lukasz.wolski.DatingAppBackend.dtos

data class HobbyUserDTO(
    val profileId: Int,
    val hobbyId: Int,
    var decision: Int,
    val name: String,
)
