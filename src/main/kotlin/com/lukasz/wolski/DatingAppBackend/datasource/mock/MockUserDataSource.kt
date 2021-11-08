package com.lukasz.wolski.DatingAppBackend.datasource.mock

import com.lukasz.wolski.DatingAppBackend.datasource.UserDataSource
import com.lukasz.wolski.DatingAppBackend.model.User
import org.springframework.stereotype.Repository

@Repository
class MockUserDataSource : UserDataSource{
    val users = listOf(User())
    override fun getUsereee(): Collection<User> {
        return users
    }
}