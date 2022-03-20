package com.lukasz.wolski.DatingAppBackend.repositories

import com.lukasz.wolski.DatingAppBackend.model.DictionaryAlcoholModel
import com.lukasz.wolski.DatingAppBackend.model.DictionaryHobbyModel
import org.springframework.data.jpa.repository.JpaRepository

interface DictionaryHobbyRepository: JpaRepository<DictionaryHobbyModel, Int> {
}