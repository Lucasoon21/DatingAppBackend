package com.lukasz.wolski.DatingAppBackend.services

import com.lukasz.wolski.DatingAppBackend.model.MatchModel
import com.lukasz.wolski.DatingAppBackend.model.ProfileModel
import com.lukasz.wolski.DatingAppBackend.model.SwipeDecisionModel
import com.lukasz.wolski.DatingAppBackend.repositories.MatchRepository
import com.lukasz.wolski.DatingAppBackend.repositories.ProfileRepository
import com.lukasz.wolski.DatingAppBackend.repositories.SwipeRepository
import com.lukasz.wolski.DatingAppBackend.repositories.TypeOrienatationRepository
import org.hibernate.query.criteria.internal.predicate.BooleanExpressionPredicate
import org.springframework.data.jpa.domain.AbstractPersistable_.id
import org.springframework.stereotype.Service

@Service
class MatchService(private val profileRepository: ProfileRepository,
                   private val matchRepository: MatchRepository,
                   private val swipeRepository: SwipeRepository,
) {

    fun save(match: MatchModel): MatchModel? {
        return this.matchRepository.save(match)
    }
    fun getAllMatch(profile: ProfileModel): List<MatchModel>? {
        return this.matchRepository.findAllByProfileFirstOrProfileSecond(profile,profile)
    }
    fun getMatch(profileFirst: ProfileModel, profileSecond: ProfileModel): MatchModel? {
        return this.matchRepository.findFirstByProfileFirstAndProfileSecond(profileFirst,profileSecond)
    }
    fun deleteMatch(id: Int) {
        return this.matchRepository.deleteById(id)
    }
    fun checkMatch(firstProfile: ProfileModel, secondProfile: ProfileModel) {

    }
}