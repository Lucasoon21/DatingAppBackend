package com.lukasz.wolski.DatingAppBackend.repositories

import com.lukasz.wolski.DatingAppBackend.model.DictionaryGenderModel
import org.springframework.data.jpa.repository.JpaRepository

interface TypeGenderRepository: JpaRepository<DictionaryGenderModel, Int> {
    fun getAllById(id: Int): DictionaryGenderModel?

}