package com.lukasz.wolski.DatingAppBackend.services

import com.lukasz.wolski.DatingAppBackend.model.TypeOrientationModel
import com.lukasz.wolski.DatingAppBackend.repositories.TypeOrienatationRepository
import org.springframework.stereotype.Service

@Service
class OrientationService(private val typeOrienatationRepository: TypeOrienatationRepository) {
    fun getOrientation(id: Int) : TypeOrientationModel? {
        return this.typeOrienatationRepository.getAllById(id)
    }
}