package com.lukasz.wolski.DatingAppBackend.dtos

import com.lukasz.wolski.DatingAppBackend.model.*
import java.sql.Date

data class ProfileDTO(
    val name:String?,
    val gender: DictionaryGenderModel,
    val orientation: DictionaryOrientationModel,
    val description: String?,
    val localicationX: String?,
    var alcohol: DictionaryAlcoholModel,
    var job: String?,
    var height: Int?,
    var weight: Double?,
    var education: DictionaryEducationModel,
    var religious: DictionaryReligiousModel,
    var children: DictionaryChildrenModel,
    var cigarettes: DictionaryCigarettesModel,
    var eyeColor: DictionaryEyeColorModel,
    var zodiac: DictionaryZodiacModel,

    )
