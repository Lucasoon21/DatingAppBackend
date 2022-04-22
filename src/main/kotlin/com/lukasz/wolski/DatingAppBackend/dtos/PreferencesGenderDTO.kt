package com.lukasz.wolski.DatingAppBackend.dtos

data class PreferencesGenderDTO(
    val profileId: Int,
    val genderId: Int,
    var decision: Int, //0 - nie zaznaczone, 1 - zaznaczone
    val name: String,
)
