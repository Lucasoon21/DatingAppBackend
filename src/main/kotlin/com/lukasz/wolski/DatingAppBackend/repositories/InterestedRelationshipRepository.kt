package com.lukasz.wolski.DatingAppBackend.repositories

import com.lukasz.wolski.DatingAppBackend.model.*
import org.springframework.data.jpa.repository.JpaRepository

interface InterestedRelationshipRepository:JpaRepository<InterestedRelationshipModel,Int> {
    fun findInterestedRelationshipModelByProfileAndRelationship(profile: ProfileModel, gender: DictionaryRelationshipModel): List<InterestedRelationshipModel>
    fun findFirstInterestedRelationshipModelByProfileAndRelationship(profileId: ProfileModel, relationshipId: DictionaryRelationshipModel): InterestedRelationshipModel
    fun findAllByProfile(profileId: ProfileModel): List<InterestedRelationshipModel>
    fun findAllByProfileAndRelationship(profile: ProfileModel, relationship: DictionaryRelationshipModel): InterestedRelationshipModel?
}