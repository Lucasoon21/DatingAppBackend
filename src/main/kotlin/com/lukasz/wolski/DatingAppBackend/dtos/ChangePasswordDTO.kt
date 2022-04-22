package com.lukasz.wolski.DatingAppBackend.dtos

data class ChangePasswordDTO(
    val profileId:Int,
    val oldPassword:String,
    val newPassword:String,
    val newConfirmPassword:String,
)
