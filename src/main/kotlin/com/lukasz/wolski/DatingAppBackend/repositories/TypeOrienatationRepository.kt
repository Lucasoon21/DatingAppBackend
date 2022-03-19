package com.lukasz.wolski.DatingAppBackend.repositories

import com.lukasz.wolski.DatingAppBackend.model.DictionaryOrientationModel
import org.springframework.data.jpa.repository.JpaRepository

interface TypeOrienatationRepository: JpaRepository<DictionaryOrientationModel, Int> {
    fun getAllById(id: Int): DictionaryOrientationModel?
}