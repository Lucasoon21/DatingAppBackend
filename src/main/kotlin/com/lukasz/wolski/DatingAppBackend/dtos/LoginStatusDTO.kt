package com.lukasz.wolski.DatingAppBackend.dtos

data class LoginStatusDTO(
    val code: Int = 0, //0 Nieprawidłowe coś, 1 - nie aktywowany, 2 - nie ma 18 lat
    val message: String = "",
)
