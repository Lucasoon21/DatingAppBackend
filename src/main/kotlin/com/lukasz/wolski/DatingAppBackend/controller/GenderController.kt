package com.lukasz.wolski.DatingAppBackend.controller

import com.lukasz.wolski.DatingAppBackend.dtos.InterestedGenderDTO
import com.lukasz.wolski.DatingAppBackend.model.InterestedGenderModel
import com.lukasz.wolski.DatingAppBackend.services.*
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("gender")
class GenderController(private val profileService: ProfileService,
                       private val genderService: GenderService,
                       private val interestedGenderService: InterestedGenderService,
                    )
{
    @PostMapping("newGenderPreferences")
    fun addGenderPreferences(@RequestBody body: InterestedGenderDTO, response: HttpServletResponse) {
        if(this.profileService.profileExistById(body.profileId)) {
            if(this.genderService.genderExistById(body.genderId)){
                val profile = this.profileService.getProfileById(body.profileId)
                val gender = this.genderService.getGenderById(body.genderId)
                val checkGenderPreferences = this.interestedGenderService.genderPreferencesExists(profile,gender)
                if(checkGenderPreferences.isEmpty()){
                    println("nie istnieje taki rekord")
                    val interestedGender = InterestedGenderModel()
                    interestedGender.decision = body.decision
                    interestedGender.profile = profile
                    interestedGender.gender = gender
                    this.interestedGenderService.save(interestedGender)
                }
                else {
                    println("istnieje taki rekord")
                }
            }
            else {
                println("taka plec nie istnieje")
            }
        }
        else {
            println("taki profil nie istnieje")
        }
    }
}