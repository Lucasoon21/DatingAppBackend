package com.lukasz.wolski.DatingAppBackend.dtos

import java.sql.Date

data class ProfileDTO(
    val profileId: Int,
    val genderId: Int,
    val orientationId: Int,
    val description: String,
    val localicationX: String,
    val localicationY: String
)
