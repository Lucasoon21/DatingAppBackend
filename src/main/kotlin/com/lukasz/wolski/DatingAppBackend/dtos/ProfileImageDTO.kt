package com.lukasz.wolski.DatingAppBackend.dtos

import java.util.*

data class ProfileImageDTO(
    val profileId: Int,
    val deleteHashImgur: String,
    val idImgur: String,
    val linkImgur: String,
    val isMainPhoto: Boolean,
)
