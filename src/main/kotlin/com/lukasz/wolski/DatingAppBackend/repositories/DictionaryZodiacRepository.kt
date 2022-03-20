package com.lukasz.wolski.DatingAppBackend.repositories

import com.lukasz.wolski.DatingAppBackend.model.DictionaryAlcoholModel
import com.lukasz.wolski.DatingAppBackend.model.DictionaryZodiacModel
import org.springframework.data.jpa.repository.JpaRepository

interface DictionaryZodiacRepository: JpaRepository<DictionaryZodiacModel, Int> {
}