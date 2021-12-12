package com.lukasz.wolski.DatingAppBackend.dtos

data class LoginDTO(
    var email: String,
    var password: String,
    var AccessToken: String?,
    var refreshToken: String?,
    )