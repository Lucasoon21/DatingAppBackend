package com.lukasz.wolski.DatingAppBackend.repositories

import com.lukasz.wolski.DatingAppBackend.model.DictionaryAlcoholModel
import com.lukasz.wolski.DatingAppBackend.model.DictionaryCigarettesModel
import org.springframework.data.jpa.repository.JpaRepository

interface DictionaryCigarettesRepository: JpaRepository<DictionaryCigarettesModel, Int> {
}