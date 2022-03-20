package com.lukasz.wolski.DatingAppBackend.repositories

import com.lukasz.wolski.DatingAppBackend.model.DictionaryAlcoholModel
import com.lukasz.wolski.DatingAppBackend.model.DictionaryReligiousModel
import org.springframework.data.jpa.repository.JpaRepository

interface DictionaryReligiousRepository: JpaRepository<DictionaryReligiousModel, Int> {
}