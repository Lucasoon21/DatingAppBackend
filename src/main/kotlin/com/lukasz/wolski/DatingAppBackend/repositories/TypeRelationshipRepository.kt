package com.lukasz.wolski.DatingAppBackend.repositories

import com.lukasz.wolski.DatingAppBackend.model.DictionaryRelationshipModel
import org.springframework.data.jpa.repository.JpaRepository

interface TypeRelationshipRepository: JpaRepository<DictionaryRelationshipModel, Int> {
    fun getAllById(id: Int): DictionaryRelationshipModel?

}