package com.lukasz.wolski.DatingAppBackend.repositories

import com.lukasz.wolski.DatingAppBackend.model.DictionaryAlcoholModel
import com.lukasz.wolski.DatingAppBackend.model.DictionaryGenderModel
import org.springframework.data.jpa.repository.JpaRepository

interface DictionaryGenderRepository: JpaRepository<DictionaryGenderModel, Int> {
}