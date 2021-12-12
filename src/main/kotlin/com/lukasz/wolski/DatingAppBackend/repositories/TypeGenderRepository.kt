package com.lukasz.wolski.DatingAppBackend.repositories

import com.lukasz.wolski.DatingAppBackend.model.TypeGenderModel
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface TypeGenderRepository: JpaRepository<TypeGenderModel, Int> {
    fun getAllById(id: Int): TypeGenderModel?

}