package com.lukasz.wolski.DatingAppBackend.dtos

import com.lukasz.wolski.DatingAppBackend.model.*
import java.sql.Date

data class ProfileDTO(
    val name:String?,
    val gender: String?,
    val orientation: String?,
    val description: String?,
    val localicationX: String?,
    var alcohol: String?,
    var job: String?,
    var height: Int?,
    var weight: Int?,
    var education: String?,
    var religious: String?,
    var children: String?,
    var cigarettes: String?,
    var eyeColor: String?,
    var zodiac: String?,
    var age: Int?,
    )
