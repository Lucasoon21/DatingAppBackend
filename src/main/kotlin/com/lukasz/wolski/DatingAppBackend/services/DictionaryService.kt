package com.lukasz.wolski.DatingAppBackend.services

import com.lukasz.wolski.DatingAppBackend.model.*
import com.lukasz.wolski.DatingAppBackend.repositories.*
import org.springframework.stereotype.Service

@Service
class DictionaryService(private val dictionaryAlcoholRepository: DictionaryAlcoholRepository,
                        private val dictionaryChildrenRepository: DictionaryChildrenRepository,
                        private val dictionaryCigarettesRepository: DictionaryCigarettesRepository,
                        private val dictionaryEducationRepository: DictionaryEducationRepository,
                        private val dictionaryEyeColorRepository: DictionaryEyeColorRepository,
                        private val dictionaryGenderRepository: DictionaryGenderRepository,
                        private val dictionaryHobbyRepository: DictionaryHobbyRepository,
                        private val dictionaryOrientationRepository: DictionaryOrientationRepository,
                        private val dictionaryRelationshipRepository: DictionaryRelationshipRepository,
                        private val dictionaryReligiousRepository: DictionaryReligiousRepository,
                        private val dictionaryZodiacRepository: DictionaryZodiacRepository,

                        ) {
    fun getAllAlcoholDictionary(): List<DictionaryAlcoholModel>? {
        return this.dictionaryAlcoholRepository.findAll()
    }
    fun getAllChildrenDictionary(): List<DictionaryChildrenModel>? {
        return this.dictionaryChildrenRepository.findAll()
    }
    fun getAllCigarettesDictionary(): List<DictionaryCigarettesModel>? {
        return this.dictionaryCigarettesRepository.findAll()
    }
    fun getAllEducationDictionary(): List<DictionaryEducationModel>? {
        return this.dictionaryEducationRepository.findAll()
    }
    fun getAllEyeColorDictionary(): List<DictionaryEyeColorModel>? {
        return this.dictionaryEyeColorRepository.findAll()
    }
    fun getAllGenderDictionary(): List<DictionaryGenderModel>? {
        return this.dictionaryGenderRepository.findAll()
    }
    fun getAllHobbyDictionary(): List<DictionaryHobbyModel>? {
        return this.dictionaryHobbyRepository.findAll()
    }
    fun getAllOrientationDictionary(): List<DictionaryOrientationModel>? {
        return this.dictionaryOrientationRepository.findAll()
    }
    fun getAllRelationshipDictionary(): List<DictionaryRelationshipModel>? {
        return this.dictionaryRelationshipRepository.findAll()
    }
    fun getAllReligiousDictionary(): List<DictionaryReligiousModel>? {
        return this.dictionaryReligiousRepository.findAll()
    }
    fun getAllZodiacDictionary(): List<DictionaryZodiacModel>? {
        return this.dictionaryZodiacRepository.findAll()
    }



    fun getAlcohol(id: Int): DictionaryAlcoholModel? {
        return this.dictionaryAlcoholRepository.getAllById(id)
    }
    fun getChildren(id: Int): DictionaryChildrenModel? {
        return this.dictionaryChildrenRepository.getAllById(id)
    }
    fun getCigarettes(id: Int): DictionaryCigarettesModel? {
        return this.dictionaryCigarettesRepository.getAllById(id)
    }
    fun getEducation(id: Int): DictionaryEducationModel? {
        return this.dictionaryEducationRepository.getAllById(id)
    }
    fun getEyeColor(id: Int): DictionaryEyeColorModel? {
        return this.dictionaryEyeColorRepository.getAllById(id)
    }
    fun getReligious(id: Int): DictionaryReligiousModel? {
        return this.dictionaryReligiousRepository.getAllById(id)
    }
    fun getZodiac(id: Int): DictionaryZodiacModel? {
        return this.dictionaryZodiacRepository.getAllById(id)
    }


}