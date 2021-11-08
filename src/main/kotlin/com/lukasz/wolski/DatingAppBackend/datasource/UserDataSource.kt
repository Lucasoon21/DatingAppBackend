package com.lukasz.wolski.DatingAppBackend.datasource

import com.lukasz.wolski.DatingAppBackend.model.User

interface UserDataSource {
    fun getUsereee(): Collection<User>
}