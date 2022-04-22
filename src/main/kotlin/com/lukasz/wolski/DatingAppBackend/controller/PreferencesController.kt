package com.lukasz.wolski.DatingAppBackend.controller

import com.lukasz.wolski.DatingAppBackend.dtos.*
import com.lukasz.wolski.DatingAppBackend.model.*
import com.lukasz.wolski.DatingAppBackend.services.*
import org.springframework.web.bind.annotation.*
import java.util.ArrayList
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("preferences")
class PreferencesController(private val interestedAgeService: InterestedAgeService,
                            private val profileService: ProfileService,
                            private val genderService: GenderService,
                            private val dictionaryService: DictionaryService,
                            private val interestedGenderService: InterestedGenderService,
                            private val hobbyService: HobbyService,
                            private val interestedHobbyService: InterestedHobbyService,
                            private val relationshipService: RelationshipService,
                            private val interestedRelationshipService: InterestedRelationshipService,
                            private val interestedHeightService: InterestedHeightService,
                            private val interestedWeightService: InterestedWeightService

                            ){


    @RequestMapping("getPreferencesGender")
    fun getPreferencesGender(@RequestParam(value = "profile") profileId: Int): ArrayList<PreferencesGenderDTO>? {
        if (this.profileService.profileExistById(profileId)) {
            val listGender = dictionaryService.getAllGenderDictionary()
            val profile = this.profileService.getProfileById(profileId)
            val genderUserPreferences  = this.interestedGenderService.getAllInterestedGenderByProfile(profile)

            // val returnListGender: ArrayList<GenderUserDTO> = emptyList<GenderUserDTO>() as ArrayList<GenderUserDTO>
            val returnListGender = ArrayList<PreferencesGenderDTO>()
            if(listGender!=null){
                for(item in listGender){
                    val gender: PreferencesGenderDTO = PreferencesGenderDTO(
                        profileId,
                        item.id,
                        1,
                        item.name
                    )
                    returnListGender.add(gender)
                }
                if (genderUserPreferences != null) {
                    for(item in genderUserPreferences){
                        val e = returnListGender.indexOfFirst { it.genderId == item.gender.id }
                        returnListGender[e].decision = item.decision
                    }
                }
                return returnListGender
            }
            else {
                println("Użytkownik nie ma wybranego gender")
            }
        } else {
            println("Nie znaleziono")
        }
        return null;
    }


    @PutMapping("changePreferencesGender")
    fun changePreferencesGender(@RequestBody body: ChangeGenderPreferencesDTO, response: HttpServletResponse) {
        val profileId = body.profileId
        val newPreferencesGenderList = body.listGender  //3
        println(newPreferencesGenderList.size)
       if (this.profileService.profileExistById(profileId)) {
            val profile = this.profileService.getProfileById(profileId)
            val listPreferencesGender =  this.interestedGenderService.getAllInterestedGenderByProfile(profile) //0
            val listGender = dictionaryService.getAllGenderDictionary() //3
           println(listPreferencesGender?.size ?: 0)
           println(listGender?.size ?: 0)

            if (listGender != null) {
                for(newGenderPreferences in newPreferencesGenderList) {
                    val gender = this.dictionaryService.getGender(newGenderPreferences.genderId)
                    if(gender != null){
                        println("Rozne od 0")
                        val oldGender = this.interestedGenderService.getInterestedGenderByProfileId(profile,gender)

                        if(oldGender != null) {
                            oldGender.decision = newGenderPreferences.decision
                            this.interestedGenderService.save(oldGender)
                        } else {
                            val newGenderModel = InterestedGenderModel()
                            newGenderModel.gender = gender
                            newGenderModel.decision = newGenderPreferences.decision
                            newGenderModel.profile = profile
                            this.interestedGenderService.save(newGenderModel)
                        }
                    } else {
                        println("nie znaleziono gender")
                    }
                }
            } else{
                println("lista gender jest pusta")
            }
        } else {
            println("nie znaleziono uzytkownika")
        }
        println(body.listGender)
    }













    @RequestMapping("getAgePreferences")
    fun getAgePreferences(@RequestParam(value = "profile") profileId: Int): InterestedAgeDTO? {

        if (this.profileService.profileExistById(profileId)) {
            val profile = profileService.getProfileById(profileId)
            val age = interestedAgeService.getInterestedAgeByProfileId(profile)
            val returnAge:InterestedAgeDTO
            if(age!=null){
                returnAge = InterestedAgeDTO(
                    age.age_from,
                    age.age_to,
                    profile.id
                )
            } else {
                returnAge = InterestedAgeDTO(
                    18,
                    100,
                    profile.id
                )
            }
            return returnAge
        } else {
            println("Nie znaleziono")
            return null
        }

    }

    @PutMapping("changeAgePreferences")
    fun editAgePreferences(@RequestBody body: InterestedAgeDTO, response: HttpServletResponse) {

        if (this.profileService.profileExistById(body.profileId)) {
            if(body.ageFrom<body.ageTo && body.ageFrom>18 && body.ageTo<100) {
                val profile = this.profileService.getProfileById(body.profileId)
                val oldAgePreferences = this.interestedAgeService.getInterestedAgeByProfileId(profile)
                if(oldAgePreferences!=null) {
                    oldAgePreferences.age_from = body.ageFrom
                    oldAgePreferences.age_to = body.ageTo
                    this.interestedAgeService.save(oldAgePreferences)
                } else {
                    val agePreferencesModel = InterestedAgeModel()
                    agePreferencesModel.age_from=body.ageFrom
                    agePreferencesModel.age_to=body.ageTo
                    agePreferencesModel.profileId=profile
                    this.interestedAgeService.save(agePreferencesModel)
                }

            } else {
                println("Błędny wiek ")
            }
        } else {
            println("taki profil nie istnieje")
        }
    }




    @RequestMapping("getPreferencesHobby")
    fun getPreferencesHobby(@RequestParam(value = "profile") profileId: Int): ArrayList<HobbyUserDTO>? {
        if (this.profileService.profileExistById(profileId)) {
            val listHobby = dictionaryService.getAllHobbyDictionary()
            val profile = this.profileService.getProfileById(profileId)
            val hobbyUserPreferences  = this.interestedHobbyService.getAllInterestedHobbyByProfile(profile)

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
                for(item in hobbyUserPreferences){
                    val e = returnListHobby.indexOfFirst { it.hobbyId == item.hobby.id }
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


    @PutMapping("changePreferencesHobby")
    fun changePreferencesHobby(@RequestBody body: ChangeProfileHobbyDTO, response: HttpServletResponse) {
        val profileId = body.profileId
        val newPreferencesHobbyList = body.listHobby
        if (this.profileService.profileExistById(profileId)) {
            val profile = this.profileService.getProfileById(profileId)
            val listPreferencesHobby =  this.interestedHobbyService.getAllInterestedHobbyByProfile(profile)
            val listHobby = dictionaryService.getAllHobbyDictionary()
            if (listHobby != null) {
                for(newHobbyPreferences in newPreferencesHobbyList) {
                    val hobby = this.dictionaryService.getHobby(newHobbyPreferences.hobbyId)
                    if(hobby != null){
                        val oldHobby = this.interestedHobbyService.getPreferencesHobbyByHobbyIdAndProfileId(profile,hobby)
                        if(oldHobby != null) {
                            oldHobby.decison = newHobbyPreferences.decision
                            this.interestedHobbyService.save(oldHobby)
                        } else {
                            val newHobbyModel = InterestedHobbyModel()
                            newHobbyModel.hobby =hobby
                            newHobbyModel.decison = newHobbyPreferences.decision
                            newHobbyModel.profile = profile
                            this.interestedHobbyService.save(newHobbyModel)
                        }
                    } else {
                        println("nie znaleziono hobby")
                    }
                }
            } else{
                println("lista hobby jest pusta")
            }
        } else {
            println("nie znaleziono uzytkownika")
        }
        println(body.listHobby)
    }





    @RequestMapping("getHeightPreferences")
    fun getHeightPreferences(@RequestParam(value = "profile") profileId: Int): InterestedHeightDTO? {

        if (this.profileService.profileExistById(profileId)) {
            val profile = profileService.getProfileById(profileId)
            val height = interestedHeightService.getInterestedHeightByProfileId(profile)
            val returnHeight:InterestedHeightDTO
            if(height!=null){
                returnHeight = InterestedHeightDTO(
                    height.height_from,
                    height.height_to,
                    profile.id
                )
            } else {
                returnHeight = InterestedHeightDTO(
                    100,
                    200,
                    profile.id
                )
            }
            return returnHeight
        } else {
            println("Nie znaleziono")
            return null
        }

    }

    @PutMapping("changeHeightPreferences")
    fun editHeightPreferences(@RequestBody body: InterestedHeightDTO, response: HttpServletResponse) {

        if (this.profileService.profileExistById(body.profileId)) {
            if(body.heightFrom<body.heightTo && body.heightFrom>=100 && body.heightTo<200) {
                val profile = this.profileService.getProfileById(body.profileId)
                var oldHeightPreferences = this.interestedHeightService.getInterestedHeightByProfileId(profile)
                if(oldHeightPreferences!=null) {
                    oldHeightPreferences.height_from = body.heightFrom
                    oldHeightPreferences.height_to = body.heightTo
                    this.interestedHeightService.save(oldHeightPreferences)
                } else {
                    val heightInterestedModel = InterestedHeightModel()
                    heightInterestedModel.height_from=body.heightFrom
                    heightInterestedModel.height_to=body.heightTo
                    heightInterestedModel.profileId=profile
                    this.interestedHeightService.save(heightInterestedModel)
                }

            } else {
                println("Błędny wzrost ")
            }
        } else {
            println("taki profil nie istnieje")
        }
    }






    @RequestMapping("getWeightPreferences")
    fun getWeightPreferences(@RequestParam(value = "profile") profileId: Int): InterestedWeightDTO? {

        if (this.profileService.profileExistById(profileId)) {
            val profile = profileService.getProfileById(profileId)
            val weight = interestedWeightService.getInterestedWeightByProfile(profile)
            val returnWeight:InterestedWeightDTO
            if(weight!=null){
                returnWeight = InterestedWeightDTO(
                    weight.weight_from,
                    weight.weight_to,
                    profile.id
                )
            } else {
                returnWeight = InterestedWeightDTO(
                    100,
                    200,
                    profile.id
                )
            }
            return returnWeight
        } else {
            println("Nie znaleziono")
            return null
        }

    }





    @PutMapping("changeWeightPreferences")
    fun editWeightPreferences(@RequestBody body: InterestedWeightDTO, response: HttpServletResponse) {


        if (this.profileService.profileExistById(body.profileId)) {
            if(body.weightFrom<body.weightTo && body.weightFrom>=30 && body.weightTo<=200) {
                val profile = this.profileService.getProfileById(body.profileId)
                var oldWeightPreferences = this.interestedWeightService.getInterestedWeightByProfile(profile)
                if(oldWeightPreferences!=null) {
                    oldWeightPreferences.weight_from = body.weightFrom
                    oldWeightPreferences.weight_to = body.weightTo
                    this.interestedWeightService.save(oldWeightPreferences)
                } else {
                    val weightInterestedModel = InterestedWeightModel()
                    weightInterestedModel.weight_from=body.weightFrom
                    weightInterestedModel.weight_to=body.weightTo
                    weightInterestedModel.profileId=profile
                    this.interestedWeightService.save(weightInterestedModel)
                }

            } else {
                println("Błędna waga ")
            }
        } else {
            println("taki profil nie istnieje")
        }
    }

}