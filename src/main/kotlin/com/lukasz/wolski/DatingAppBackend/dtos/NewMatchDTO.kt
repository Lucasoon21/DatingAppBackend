package com.lukasz.wolski.DatingAppBackend.dtos

import com.lukasz.wolski.DatingAppBackend.model.ImageUserModel

data class NewMatchDTO(
    val profileId: Int,
    val name: String,
    val profileImageDTO: ImageUserModel?

)