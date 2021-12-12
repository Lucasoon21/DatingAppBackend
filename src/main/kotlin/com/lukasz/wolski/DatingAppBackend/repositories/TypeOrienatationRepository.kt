package com.lukasz.wolski.DatingAppBackend.repositories

import com.lukasz.wolski.DatingAppBackend.model.TypeOrientationModel
import org.springframework.data.jpa.repository.JpaRepository

interface TypeOrienatationRepository: JpaRepository<TypeOrientationModel, Int> {
    fun getAllById(id: Int): TypeOrientationModel?
}