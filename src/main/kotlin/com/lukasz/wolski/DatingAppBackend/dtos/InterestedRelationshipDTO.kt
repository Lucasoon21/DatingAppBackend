package com.lukasz.wolski.DatingAppBackend.dtos

data class InterestedRelationshipDTO(
    val profileId: Int,
    val relationshipId: Int,
    val decision: Int,
)
