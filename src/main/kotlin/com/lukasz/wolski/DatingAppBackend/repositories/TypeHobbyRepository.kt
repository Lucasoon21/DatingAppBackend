package com.lukasz.wolski.DatingAppBackend.repositories

import com.lukasz.wolski.DatingAppBackend.model.TypeGenderModel
import com.lukasz.wolski.DatingAppBackend.model.TypeHobbyModel
import org.springframework.data.jpa.repository.JpaRepository

interface TypeHobbyRepository: JpaRepository<TypeHobbyModel, Int> {
    fun getAllById(id: Int): TypeHobbyModel?
}