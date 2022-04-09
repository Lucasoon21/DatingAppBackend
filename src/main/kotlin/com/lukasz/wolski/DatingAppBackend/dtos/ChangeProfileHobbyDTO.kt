package com.lukasz.wolski.DatingAppBackend.dtos

data class ChangeProfileHobbyDTO(
    var profileId: Int,
    var listHobby: List<HobbyUserDTO>
)
