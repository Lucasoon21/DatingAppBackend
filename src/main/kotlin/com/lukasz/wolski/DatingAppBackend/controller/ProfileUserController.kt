package com.lukasz.wolski.DatingAppBackend.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.lukasz.wolski.DatingAppBackend.dtos.ChangeDescriptionDTO
import com.lukasz.wolski.DatingAppBackend.dtos.ProfileDTO
import com.lukasz.wolski.DatingAppBackend.dtos.ChangeProfileDetailsDTO
import com.lukasz.wolski.DatingAppBackend.dtos.GetProfileDTO
import com.lukasz.wolski.DatingAppBackend.services.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("profile")
class ProfileUserController(
    private val userService: UserService,
    private val profileService: ProfileService,
    private val genderService: GenderService,
    private val dictionaryService: DictionaryService,
    private val orientationService: OrientationService,
) {
/*
    @PutMapping("edit")
    fun editProfile(@RequestBody body: ProfileDTO, response: HttpServletResponse) {
        println("edycja profilu")
        if (this.profileService.profileExistById(body.profileId)) {
            if (this.genderService.genderExistById(body.genderId) && this.orientationService.orientationExistsById(body.orientationId)) {
                val gender = this.genderService.getGender(body.genderId)
                val orientation = this.orientationService.getOrientation(body.orientationId)
                val oldProfile = this.profileService.getProfileById(body.profileId)
                if (gender != null) {
                    oldProfile.dictionaryGender = gender
                }
                if (orientation != null) {
                    oldProfile.dictionaryOrientation = orientation
                }
                oldProfile.description = body.description
                this.profileService.save(oldProfile)
            } else {
                println("Nie istnieje płeć lub orientacja")
            }
        } else {
            println("taki profile nie istnieje")
        }
    }
*/
    @PutMapping("getProfileDetails")
    fun getProfileDetails(@RequestBody body: GetProfileDTO, response: HttpServletResponse): ResponseEntity<ProfileDTO> {
        println("Szczegóły profilu"+body.profileId)
        if (this.profileService.profileExistById(body.profileId)) {
            val oldProfile = this.profileService.getProfileById(body.profileId)
            if(oldProfile!=null) {
                val responseProfile:ProfileDTO = ProfileDTO(
                    oldProfile.name,
                    oldProfile.dictionaryGender,
                    oldProfile.dictionaryOrientation,
                    oldProfile.description,
                    oldProfile.location,
                    oldProfile.dictionaryAlcohol,
                    oldProfile.job,
                    oldProfile.height,
                    oldProfile.weight,
                    oldProfile.dictionaryEducation,
                    oldProfile.dictionaryReligious,
                    oldProfile.dictionaryChildren,
                    oldProfile.dictionaryCigarettes,
                    oldProfile.dictionaryEyeColor,
                    oldProfile.dictionaryZodiac
                )


                ObjectMapper().writeValue(response.outputStream, responseProfile)
                return ResponseEntity.ok().body(responseProfile)
            } else {
                return ResponseEntity.ok().body(null)
            }

        } else {
            println("taki profile nie istnieje")
            return ResponseEntity.badRequest().body(null)
        }
    }

    @PutMapping("changeDescription")
    fun changeDescription(@RequestBody body: ChangeDescriptionDTO, response: HttpServletResponse): ResponseEntity<String> {
        println("Zmiana opisu")
        if (this.profileService.profileExistById(body.profileId)) {
            val oldProfile = this.profileService.getProfileById(body.profileId)
            oldProfile.description = body.description
            this.profileService.save(oldProfile)
            println("Zmieniono opis")
            return ResponseEntity.ok().body("Zmieniono opis\n")
        } else {
            println("taki profile nie istnieje")
            return ResponseEntity.badRequest().body("Nie zminiono opisu\n")
        }
    }


    @PutMapping("changeProfileDetails")
    fun changeProfileDetails(@RequestBody body: ChangeProfileDetailsDTO, response: HttpServletResponse): ResponseEntity<String> {
        println("Zmiana szczegółów profilu")
        if (this.profileService.profileExistById(body.profileId)) {
            println(body.profileId)
            val oldProfile = profileService.getProfileById(body.profileId)
            val orientation = orientationService.getOrientation(body.orientationId)
            val height = body.height
            val weight = body.weight
            val alcohol = dictionaryService.getAlcohol(body.alcoholId)
            val children = dictionaryService.getChildren(body.childrenId)
            val cigarettes = dictionaryService.getCigarettes(body.cigarettesId)
            val education = dictionaryService.getEducation(body.educationId)
            val eyeColor = dictionaryService.getEyeColor(body.eyeColorId)
            val religious = dictionaryService.getReligious(body.religiousId)
            val job = body.job
            //val zodiac = dictionaryService.getZodiac(body.zodiacId)

            if(orientation != null && (height in 100..200) && (weight in 30.0..150.0) && alcohol!=null
                && children!=null && cigarettes!=null && education!=null && eyeColor!=null
                    && religious!=null) {
                oldProfile.height = height
                oldProfile.weight = weight
                oldProfile.dictionaryOrientation = orientation
                oldProfile.dictionaryAlcohol = alcohol
                oldProfile.dictionaryChildren = children
                oldProfile.dictionaryCigarettes = cigarettes
                oldProfile.dictionaryEducation = education
                oldProfile.dictionaryEyeColor = eyeColor
                oldProfile.dictionaryReligious = religious
                oldProfile.job = job
                this.profileService.save(oldProfile)
                return ResponseEntity.ok().body("Zmieniono szczegóły profilu\n")


            } else {
                println("Więcej niż jedno pole jest puste")
                return ResponseEntity.badRequest().body("Jedno pole lub więcej jest puste\n")
            }
        } else {
            println("taki profile nie istnieje")
            return ResponseEntity.badRequest().body("Nie znaleziono użytkownika\n")
        }
    }

}