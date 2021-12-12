package com.lukasz.wolski.DatingAppBackend.repositories

import com.lukasz.wolski.DatingAppBackend.model.*
import org.springframework.data.jpa.repository.JpaRepository

interface InterestedRelationshipRepository:JpaRepository<InterestedRelationshipModel,Int> {
fun findInterestedRelationshipModelByProfileAndRelationship(profile: ProfileModel, gender: TypeRelationshipModel): List<InterestedRelationshipModel>
}