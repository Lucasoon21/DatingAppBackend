package com.lukasz.wolski.DatingAppBackend.repositories

import com.lukasz.wolski.DatingAppBackend.model.DictionaryAlcoholModel
import com.lukasz.wolski.DatingAppBackend.model.DictionaryOrientationModel
import org.springframework.data.jpa.repository.JpaRepository

interface DictionaryOrientationRepository: JpaRepository<DictionaryOrientationModel, Int> {
}