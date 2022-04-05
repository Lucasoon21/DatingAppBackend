package com.lukasz.wolski.DatingAppBackend.services

import com.lukasz.wolski.DatingAppBackend.model.DictionaryGenderModel
import com.lukasz.wolski.DatingAppBackend.repositories.TypeGenderRepository
import org.springframework.stereotype.Service

@Service
class GenderService(private val typeGenderRepository: TypeGenderRepository) {
    fun getGender(id: Int): DictionaryGenderModel? {
        return this.typeGenderRepository.getAllById(id)
    }
    fun genderExistById(id: Int): Boolean {
        return this.typeGenderRepository.existsById(id)
    }
    fun getGenderById(id: Int): DictionaryGenderModel {
        return this.typeGenderRepository.getById(id)
    }
}