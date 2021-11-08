package com.lukasz.wolski.DatingAppBackend.datasource.mock

import com.lukasz.wolski.DatingAppBackend.datasource.UserDataSource
import com.lukasz.wolski.DatingAppBackend.model.UserInfo
import org.springframework.stereotype.Repository

@Repository
class MockUserDataSource : UserDataSource{
    val users = listOf(UserInfo())
    override fun getUsereee(): Collection<UserInfo> {
        return users
    }
}