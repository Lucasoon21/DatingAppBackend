package com.lukasz.wolski.DatingAppBackend.dtos

data class ChangeGenderPreferencesDTO (
    var profileId: Int,
    var listGender: List<PreferencesGenderDTO>
    )
