package com.lukasz.wolski.DatingAppBackend.repositories

import com.lukasz.wolski.DatingAppBackend.model.DictionaryAlcoholModel
import com.lukasz.wolski.DatingAppBackend.model.DictionaryChildrenModel
import org.springframework.data.jpa.repository.JpaRepository

interface DictionaryChildrenRepository: JpaRepository<DictionaryChildrenModel, Int> {
}