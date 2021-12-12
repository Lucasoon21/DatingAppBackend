package com.lukasz.wolski.DatingAppBackend.controller

import com.lukasz.wolski.DatingAppBackend.dtos.InterestedAgeDTO
import com.lukasz.wolski.DatingAppBackend.model.InterestedAgeModel
import com.lukasz.wolski.DatingAppBackend.services.*
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletResponse
@RestController
@RequestMapping("age")
class AgeController(private val interestedAgeService: InterestedAgeService,
                    private val profileService: ProfileService,
                    private val genderService: GenderService,
                    private val interestedGenderService: InterestedGenderService,
                    private val hobbyService: HobbyService,
                    private val interestedHobbyService: InterestedHobbyService,
                    private val relationshipService: RelationshipService,
                    private val interestedRelationshipService: InterestedRelationshipService,
) {
    @PostMapping("newAgePreferences")
    fun addAgePreferences(@RequestBody body: InterestedAgeDTO, response: HttpServletResponse)
    {
        if(this.profileService.profileExistById(body.profileId)){
            val profile = this.profileService.getProfileById(body.profileId)
            if(body.ageFrom<body.ageTo && body.ageFrom>18 && body.ageTo<100) {
                println("Wiek prawidlowy")
                val checkAgePreferences = interestedAgeService.profileExist(profile)
                if(checkAgePreferences.isEmpty()){
                    val agePreferencesModel = InterestedAgeModel()
                    agePreferencesModel.age_from=body.ageFrom
                    agePreferencesModel.age_to=body.ageTo
                    agePreferencesModel.profile=profile
                    this.interestedAgeService.save(agePreferencesModel)
                    println("Wiek nie istnieje")
                }
                else {
                    println("Wiek istnieje")
                }
            }
            else {
                println("Wiek jest nieprawidlowy")
            }
        }
        else {
            println("profil nie istnieje")
        }

    }

}