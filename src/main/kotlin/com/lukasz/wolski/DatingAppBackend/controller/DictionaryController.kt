package com.lukasz.wolski.DatingAppBackend.controller


import com.lukasz.wolski.DatingAppBackend.model.*
import com.lukasz.wolski.DatingAppBackend.services.DictionaryService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("dictionary")
class DictionaryController(private val dictionaryService: DictionaryService) {

    @GetMapping("getAllAlcoholDictionary")
    fun getAllAlcoholDictionary(response: HttpServletResponse): List<DictionaryAlcoholModel>? {
        return dictionaryService.getAllAlcoholDictionary()
    }
    @GetMapping("getAllChildrenDictionary")
    fun getAllChildrenDictionary(response: HttpServletResponse): List<DictionaryChildrenModel>? {
        return dictionaryService.getAllChildrenDictionary()
    }
    @GetMapping("getAllCigarettesDictionary")
    fun getAllCigarettesDictionary(response: HttpServletResponse): List<DictionaryCigarettesModel>? {
        return dictionaryService.getAllCigarettesDictionary()
    }
    @GetMapping("getAllEducationDictionary")
    fun getAllEducationDictionary(response: HttpServletResponse): List<DictionaryEducationModel>? {
        return dictionaryService.getAllEducationDictionary()
    }
    @GetMapping("getAllEyeColorDictionary")
    fun getAllEyeColorDictionary(response: HttpServletResponse): List<DictionaryEyeColorModel>? {
        return dictionaryService.getAllEyeColorDictionary()
    }
    @GetMapping("getAllGenderDictionary")
    fun getAllGenderDictionary(response: HttpServletResponse): List<DictionaryGenderModel>? {
        return dictionaryService.getAllGenderDictionary()
    }
    @GetMapping("getAllHobbyDictionary")
    fun getAllHobbyDictionary(response: HttpServletResponse): List<DictionaryHobbyModel>? {
        return dictionaryService.getAllHobbyDictionary()
    }
    @GetMapping("getAllOrientationDictionary")
    fun getAllOrientationDictionary(response: HttpServletResponse): List<DictionaryOrientationModel>? {
        return dictionaryService.getAllOrientationDictionary()
    }
    @GetMapping("getAllRelationshipDictionary")
    fun getAllRelationshipDictionary(response: HttpServletResponse): List<DictionaryRelationshipModel>? {
        return dictionaryService.getAllRelationshipDictionary()
    }
    @GetMapping("getAllReligiousDictionary")
    fun getAllReligiousDictionary(response: HttpServletResponse): List<DictionaryReligiousModel>? {
        return dictionaryService.getAllReligiousDictionary()
    }

}