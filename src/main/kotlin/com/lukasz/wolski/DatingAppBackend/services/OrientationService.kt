package com.lukasz.wolski.DatingAppBackend.services

import com.lukasz.wolski.DatingAppBackend.model.DictionaryOrientationModel
import com.lukasz.wolski.DatingAppBackend.repositories.TypeOrienatationRepository
import org.springframework.stereotype.Service

@Service
class OrientationService(private val typeOrienatationRepository: TypeOrienatationRepository) {
    fun getOrientation(id: Int) : DictionaryOrientationModel? {
        return this.typeOrienatationRepository.getAllById(id)
    }

    fun orientationExistsById(id: Int) : Boolean {
        return this.typeOrienatationRepository.existsById(id)
    }
    fun getOrientationById(id: Int) : DictionaryOrientationModel? {
        return this.typeOrienatationRepository.getById(id)
    }
}