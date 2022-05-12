package com.lukasz.wolski.DatingAppBackend.dtos

data class ChangeProfileDetailsDTO(
    var profileId: Int,
    var alcoholId: Int,
    var job: String,
    var height: Int,
    var weight: Int,
    var orientationId: Int,
    var educationId: Int,
    var religiousId: Int,
    var childrenId: Int,
    var cigarettesId: Int,
    var eyeColorId: Int,
    val city: String,
)
