package com.lukasz.wolski.DatingAppBackend.repositories

import com.lukasz.wolski.DatingAppBackend.model.TypeGenderModel
import com.lukasz.wolski.DatingAppBackend.model.TypeRelationshipModel
import org.springframework.data.jpa.repository.JpaRepository

interface TypeRelationshipRepository: JpaRepository<TypeRelationshipModel, Int> {
    fun getAllById(id: Int): TypeRelationshipModel?

}