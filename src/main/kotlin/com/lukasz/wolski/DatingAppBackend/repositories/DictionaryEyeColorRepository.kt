package com.lukasz.wolski.DatingAppBackend.repositories

import com.lukasz.wolski.DatingAppBackend.model.DictionaryAlcoholModel
import com.lukasz.wolski.DatingAppBackend.model.DictionaryEyeColorModel
import org.springframework.data.jpa.repository.JpaRepository

interface DictionaryEyeColorRepository: JpaRepository<DictionaryEyeColorModel, Int> {
}