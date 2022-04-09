package com.lukasz.wolski.DatingAppBackend.dtos

data class HobbyUserDTO(
    val profileId: Int,
    val hobbyId: Int,
    var decision: Int, //0 - nie zaznaczone, 1 - zaznaczone
    val name: String,
)
