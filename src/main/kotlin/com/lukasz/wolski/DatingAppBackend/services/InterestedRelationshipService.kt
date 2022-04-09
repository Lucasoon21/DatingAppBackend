package com.lukasz.wolski.DatingAppBackend.services

import com.lukasz.wolski.DatingAppBackend.dtos.InterestedRelationshipDTO
import com.lukasz.wolski.DatingAppBackend.model.*
import com.lukasz.wolski.DatingAppBackend.repositories.InterestedRelationshipRepository
import org.springframework.stereotype.Service

@Service
class InterestedRelationshipService(private val interstedRelationshipRepository: InterestedRelationshipRepository) {
    fun save(interestedRelationship: InterestedRelationshipModel): InterestedRelationshipModel {
        return this.interstedRelationshipRepository.save(interestedRelationship)
    }
    fun relationshipPreferencesExists(profile: ProfileModel, gender: DictionaryRelationshipModel): List<InterestedRelationshipModel> {
        return this.interstedRelationshipRepository.findInterestedRelationshipModelByProfileAndRelationship(profile,gender)
    }
    fun getInterestedRelationshipByProfileId(idProfile: ProfileModel, idRelationship: DictionaryRelationshipModel): InterestedRelationshipModel {
        return this.interstedRelationshipRepository.findFirstInterestedRelationshipModelByProfileAndRelationship(idProfile, idRelationship)
    }
    fun getAllInterestedRelationshipByProfile(profile: ProfileModel):List<InterestedRelationshipModel> {
        return this.interstedRelationshipRepository.findAllByProfile(profile)
    }
    fun getRelationshipUserByProfileAndRelationshipDictionary(profile: ProfileModel, relationship: DictionaryRelationshipModel): InterestedRelationshipModel? {
        return this.interstedRelationshipRepository.findAllByProfileAndRelationship(profile,relationship)
    }
}