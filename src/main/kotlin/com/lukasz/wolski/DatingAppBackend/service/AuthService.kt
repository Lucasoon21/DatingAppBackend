package com.lukasz.wolski.DatingAppBackend.service

import com.lukasz.wolski.DatingAppBackend.datasource.UserDataSource
import com.lukasz.wolski.DatingAppBackend.model.UserInfo
import org.springframework.stereotype.Service

@Service
class AuthService(private val dataSource: UserDataSource) {
    fun getUsers(): Collection<UserInfo> {
        return dataSource.getUsereee()
    }
    fun hello(): String {
        return "Hello"
    }
}