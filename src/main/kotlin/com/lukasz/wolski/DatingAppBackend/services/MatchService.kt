package com.lukasz.wolski.DatingAppBackend.services

import com.lukasz.wolski.DatingAppBackend.model.ProfileModel
import com.lukasz.wolski.DatingAppBackend.repositories.MatchRepository
import com.lukasz.wolski.DatingAppBackend.repositories.ProfileRepository
import com.lukasz.wolski.DatingAppBackend.repositories.TypeOrienatationRepository
import org.hibernate.query.criteria.internal.predicate.BooleanExpressionPredicate
import org.springframework.data.jpa.domain.AbstractPersistable_.id
import org.springframework.stereotype.Service

@Service
class MatchService(private val profileRepository: ProfileRepository,
    private val matchRepository: MatchRepository
) {
    fun getAllProfiles(profile: ProfileModel): List<ProfileModel>? {
        return this.profileRepository.findAllByIdNot(profile.id)
    }
}