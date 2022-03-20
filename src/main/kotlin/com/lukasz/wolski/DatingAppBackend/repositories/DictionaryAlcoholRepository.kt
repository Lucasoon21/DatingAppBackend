package com.lukasz.wolski.DatingAppBackend.repositories

import com.lukasz.wolski.DatingAppBackend.model.DictionaryAlcoholModel
import org.springframework.data.jpa.repository.JpaRepository

interface DictionaryAlcoholRepository:JpaRepository<DictionaryAlcoholModel,Int> {

}