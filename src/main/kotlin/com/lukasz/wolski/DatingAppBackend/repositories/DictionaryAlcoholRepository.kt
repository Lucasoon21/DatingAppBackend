package com.lukasz.wolski.DatingAppBackend.repositories

import com.lukasz.wolski.DatingAppBackend.model.DictionaryAlcoholModel
import com.lukasz.wolski.DatingAppBackend.model.DictionaryOrientationModel
import org.springframework.data.jpa.repository.JpaRepository

interface DictionaryAlcoholRepository:JpaRepository<DictionaryAlcoholModel,Int> {
    fun getAllById(id: Int): DictionaryAlcoholModel?
}