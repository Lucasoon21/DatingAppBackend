package com.lukasz.wolski.DatingAppBackend.datasource

import com.lukasz.wolski.DatingAppBackend.model.UserInfo

interface UserDataSource {
    fun getUsereee(): Collection<UserInfo>
}