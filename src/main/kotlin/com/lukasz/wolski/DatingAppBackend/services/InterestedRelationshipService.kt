package com.lukasz.wolski.DatingAppBackend.services

import com.lukasz.wolski.DatingAppBackend.model.*
import com.lukasz.wolski.DatingAppBackend.repositories.InterestedRelationshipRepository
import org.springframework.stereotype.Service

@Service
class InterestedRelationshipService(private val interstedRelationshipRepository: InterestedRelationshipRepository) {
    fun save(interestedRelationship: InterestedRelationshipModel): InterestedRelationshipModel {
        return this.interstedRelationshipRepository.save(interestedRelationship)
    }
    fun relationshipPreferencesExists(profile: ProfileModel, gender: TypeRelationshipModel): List<InterestedRelationshipModel> {
        return this.interstedRelationshipRepository.findInterestedRelationshipModelByProfileAndRelationship(profile,gender)
    }
}