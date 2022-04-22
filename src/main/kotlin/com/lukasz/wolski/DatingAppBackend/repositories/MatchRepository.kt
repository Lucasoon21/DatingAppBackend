package com.lukasz.wolski.DatingAppBackend.repositories

import com.lukasz.wolski.DatingAppBackend.model.MatchModel
import com.lukasz.wolski.DatingAppBackend.model.ProfileModel
import org.hibernate.query.criteria.internal.predicate.BooleanExpressionPredicate
import org.springframework.data.jpa.repository.JpaRepository

interface MatchRepository: JpaRepository<MatchModel, Int> {

    fun findAllByProfileFirstOrProfileSecond(profileFirst: ProfileModel, profileSecond: ProfileModel): List<MatchModel>?
    fun findFirstByProfileFirstAndProfileSecond(profileFirst: ProfileModel, profileSecond: ProfileModel): MatchModel?
}