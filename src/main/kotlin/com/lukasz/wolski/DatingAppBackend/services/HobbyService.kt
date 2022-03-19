package com.lukasz.wolski.DatingAppBackend.services

import com.lukasz.wolski.DatingAppBackend.model.DictionaryHobbyModel
import com.lukasz.wolski.DatingAppBackend.repositories.TypeHobbyRepository
import org.springframework.stereotype.Service

@Service
class HobbyService(private val typeHobbyRepository: TypeHobbyRepository) {
    fun getHobby(id: Int): DictionaryHobbyModel? {
        return this.typeHobbyRepository.getAllById(id)
    }
    fun hobbyExistById(id: Int): Boolean {
        return this.typeHobbyRepository.existsById(id)
    }
    fun getHobbyById(id: Int): DictionaryHobbyModel {
        return this.typeHobbyRepository.getById(id)
    }
}