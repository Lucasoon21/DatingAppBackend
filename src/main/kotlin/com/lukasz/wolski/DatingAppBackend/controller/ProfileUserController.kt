package com.lukasz.wolski.DatingAppBackend.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.lukasz.wolski.DatingAppBackend.dtos.*
import com.lukasz.wolski.DatingAppBackend.model.HobbyUserModel
import com.lukasz.wolski.DatingAppBackend.model.InterestedRelationshipModel
import com.lukasz.wolski.DatingAppBackend.services.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.ArrayList
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("profile")
class ProfileUserController(
    private val userService: UserService,
    private val profileService: ProfileService,
    private val genderService: GenderService,
    private val dictionaryService: DictionaryService,
    private val orientationService: OrientationService,
    private val hobbyUserService: HobbyUserService,
    private val interestedRelationshipService: InterestedRelationshipService
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



    @RequestMapping("getProfileHobby")
    fun getProfileHobby(@RequestParam(value = "profile") profileId: Int): ArrayList<HobbyUserDTO>? {
        if (this.profileService.profileExistById(profileId)) {
            val listHobby = dictionaryService.getAllHobbyDictionary()
            val profile = this.profileService.getProfileById(profileId)
            val hobbyUser  = this.hobbyUserService.getAllHobbyByProfile(profile)
            println(hobbyUser.size)
           // val returnListHobby: ArrayList<HobbyUserDTO> = emptyList<HobbyUserDTO>() as ArrayList<HobbyUserDTO>
            val returnListHobby = ArrayList<HobbyUserDTO>()
            if(listHobby!=null){
                for(item in listHobby){
                    val hobby: HobbyUserDTO = HobbyUserDTO(
                        profileId,
                        item.id,
                        0,
                        item.name
                    )
                    returnListHobby.add(hobby)
                }
                for(item in hobbyUser){
                    println("item")
                    val e = returnListHobby.indexOfFirst { it.hobbyId == item.hobby.id }
                    println("e = "+e)
                    println("return = "+returnListHobby[e].decision)
                    println("return = "+item.decison)

                    returnListHobby[e].decision = item.decison
                }
                return returnListHobby
            }
            else {
                println("Użytkownik nie ma wybranego hobby")
            }


        } else {
            println("Nie znaleziono")
        }
        return null;
    }


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


    @RequestMapping("getProfileRelationship")
    fun getProfileRelationship(@RequestParam(value = "profile") profileId: Int): ArrayList<InterestedRelationshipDTO>? {
        if (this.profileService.profileExistById(profileId)) {
            val listRelationship = dictionaryService.getAllRelationshipDictionary()
            val profile = this.profileService.getProfileById(profileId)
            val relationshipUser  = this.interestedRelationshipService.getAllInterestedRelationshipByProfile(profile)
            println(relationshipUser.size)
            // val returnListHobby: ArrayList<HobbyUserDTO> = emptyList<HobbyUserDTO>() as ArrayList<HobbyUserDTO>
            val returnListRelationship = ArrayList<InterestedRelationshipDTO>()
            if(listRelationship!=null){
                for(item in listRelationship){
                    val relationship: InterestedRelationshipDTO = InterestedRelationshipDTO(
                        profileId,
                        item.id,
                        0,
                        item.name
                    )
                    returnListRelationship.add(relationship)
                }
                for(item in relationshipUser){
                    println("item")
                    val e = returnListRelationship.indexOfFirst { it.relationshipId == item.relationship.id }
                    println("e = "+e)
                    println("return = "+returnListRelationship[e].decision)
                    println("return = "+item.decison)

                    returnListRelationship[e].decision = item.decison
                }
                return returnListRelationship
            }
            else {
                println("Użytkownik nie ma wybranego hobby")
            }
        } else {
            println("Nie znaleziono")
        }
        return null;
    }

    @PutMapping("changeProfileRelationship")
    fun changeProfileRelationship(@RequestBody body: ChangeProfileRelationshipDTO, response: HttpServletResponse) {
        val profileId = body.profileId
        val newRelationshipList = body.listRelationship

        println("profile nr "+profileId+" xddd "+body.listRelationship.last().decision)
        if (this.profileService.profileExistById(profileId)) {
            println("uzytkownik istnieje")
            val profile = this.profileService.getProfileById(profileId)
            val listRelationshipUser =  this.interestedRelationshipService.getAllInterestedRelationshipByProfile(profile)
            val listRelationship = dictionaryService.getAllRelationshipDictionary()
            if (listRelationship != null) {
                println("lista nie jest pusta")
                for(newRelationship in newRelationshipList) {
                    val relationship = this.dictionaryService.getRelationship(newRelationship.relationshipId)
                    if(relationship != null){
                        println("relacja nie jest pusta "+profile.id+" "+relationship.id)
                        println("new "+newRelationship.decision)
                        val oldRelationship = this.interestedRelationshipService.getRelationshipUserByProfileAndRelationshipDictionary(profile, relationship)
                        if(oldRelationship!=null){
                            oldRelationship.decison = newRelationship.decision
                            this.interestedRelationshipService.save(oldRelationship)
                        } else {
                            val newRelationshipModel = InterestedRelationshipModel()
                            newRelationshipModel.relationship =relationship
                            newRelationshipModel.decison = newRelationship.decision
                            newRelationshipModel.profile = profile
                            this.interestedRelationshipService.save(newRelationshipModel)
                        }
                        //println(oldRelationship.id)
                    } else {
                        println("nie znaleziono relacji")
                    }
                }
            } else{
                println("lista relacji jest pusta")
            }
        } else {
            println("nie znaleziono uzytkownika")
        }
        println("ddd")
    }

    @PutMapping("changeProfileHobby")
    fun changeProfileHobby(@RequestBody body: ChangeProfileHobbyDTO, response: HttpServletResponse) {
        val profileId = body.profileId
        val newHobbyList = body.listHobby
        if (this.profileService.profileExistById(profileId)) {
            val profile = this.profileService.getProfileById(profileId)
            val listHobbyUser =  this.hobbyUserService.getAllHobbyByProfile(profile)
            val listHobby = dictionaryService.getAllHobbyDictionary()
            if (listHobby != null) {
                for(newHobby in newHobbyList) {
                    val hobby = this.dictionaryService.getHobby(newHobby.hobbyId)
                    if(hobby != null){
                        val oldHobby = this.hobbyUserService.getHobbyUserByHobbyIdAndProfileId(profile,hobby)
                        if(oldHobby != null) {
                            oldHobby.decison = newHobby.decision
                            this.hobbyUserService.save(oldHobby)
                        } else {
                            val newHobbyModel = HobbyUserModel()
                            newHobbyModel.hobby =hobby
                            newHobbyModel.decison = newHobby.decision
                            newHobbyModel.profile = profile
                            this.hobbyUserService.save(newHobbyModel)
                        }
                    } else {
                        println("nie znaleziono hobby")
                    }
                    /* } else {
                         val hobby = this.dictionaryService.getHobby(newHobby.hobbyId)

                         if (hobby != null) {
                             val newHobbyModel = HobbyUserModel()
                             newHobbyModel.hobby =hobby
                             newHobbyModel.decison = newHobby.decision
                             newHobbyModel.profile = profile
                             this.hobbyUserService.save(newHobbyModel)
                         }else {
                             println("nie znaleziono hobby")
                         }
                     }*/
                }
            } else{
                println("lista hobby jest pusta")
            }
        } else {
            println("nie znaleziono uzytkownika")
        }
        println(body.listHobby)
    }

}