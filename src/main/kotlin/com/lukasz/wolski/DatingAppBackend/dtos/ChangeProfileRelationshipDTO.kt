package com.lukasz.wolski.DatingAppBackend.dtos

data class ChangeProfileRelationshipDTO(
    var profileId: Int,
    var listRelationship: List<InterestedRelationshipDTO>
)
