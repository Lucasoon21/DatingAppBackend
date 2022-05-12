package com.lukasz.wolski.DatingAppBackend.services

import com.lukasz.wolski.DatingAppBackend.model.DictionaryRelationshipModel
import com.lukasz.wolski.DatingAppBackend.repositories.TypeRelationshipRepository
import org.springframework.stereotype.Service

@Service
class RelationshipService(private val typeRelationshipRepository: TypeRelationshipRepository) {
    fun getHobby(id: Int): DictionaryRelationshipModel? {
        return this.typeRelationshipRepository.getAllById(id)
    }

    fun relationshipExistById(id: Int): Boolean {
        return this.typeRelationshipRepository.existsById(id)
    }

    fun getRelationshipById(id: Int): DictionaryRelationshipModel {
        return this.typeRelationshipRepository.getById(id)
    }
}


