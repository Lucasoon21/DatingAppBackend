package com.lukasz.wolski.DatingAppBackend.repositories

import com.lukasz.wolski.DatingAppBackend.model.DictionaryHobbyModel
import org.springframework.data.jpa.repository.JpaRepository

interface TypeHobbyRepository: JpaRepository<DictionaryHobbyModel, Int> {
    fun getAllById(id: Int): DictionaryHobbyModel?
}