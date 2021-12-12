package com.lukasz.wolski.DatingAppBackend.services

import com.lukasz.wolski.DatingAppBackend.model.TypeRelationshipModel
import com.lukasz.wolski.DatingAppBackend.repositories.TypeRelationshipRepository
import org.springframework.stereotype.Service

@Service
class RelationshipService(private val typeRelationshipRepository: TypeRelationshipRepository) {
    fun getHobby(id: Int): TypeRelationshipModel? {
        return this.typeRelationshipRepository.getAllById(id)
    }

    fun relationshipExistById(id: Int): Boolean {
        return this.typeRelationshipRepository.existsById(id)
    }

    fun getRelationshipById(id: Int): TypeRelationshipModel {
        return this.typeRelationshipRepository.getById(id)
    }
}