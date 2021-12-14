package com.lukasz.wolski.DatingAppBackend.controller

import com.lukasz.wolski.DatingAppBackend.dtos.InterestedHobbyDTO
import com.lukasz.wolski.DatingAppBackend.dtos.HobbyUserDTO
import com.lukasz.wolski.DatingAppBackend.dtos.InterestedRelationshipDTO
import com.lukasz.wolski.DatingAppBackend.model.HobbyUserModel
import com.lukasz.wolski.DatingAppBackend.model.InterestedHobbyModel
import com.lukasz.wolski.DatingAppBackend.services.*
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("hobby")
class HobbyController(private val profileService: ProfileService,
                      private val hobbyService: HobbyService,
                      private val interestedHobbyService: InterestedHobbyService,
                      private val hobbyUserService: HobbyUserService,
) {
    @PostMapping("newHobbyPreferences")
    fun addHobbyPreferences(@RequestBody body: InterestedHobbyDTO, response: HttpServletResponse) {
        if(this.profileService.profileExistById(body.profileId)) {
            if(this.hobbyService.hobbyExistById(body.hobbyId)){
                val profile = this.profileService.getProfileById(body.profileId)
                val hobby = this.hobbyService.getHobbyById(body.hobbyId)
                val checkHobbyInterested = this.interestedHobbyService.hobbyInterestedExists(profile,hobby)
                if(checkHobbyInterested.isEmpty()){
                    println("nie istnieje taki rekord")
                    val interestedHobby = InterestedHobbyModel()
                    interestedHobby.decison = body.decision
                    interestedHobby.profile = profile
                    interestedHobby.hobby = hobby
                    this.interestedHobbyService.save(interestedHobby)
                }
                else {
                    println("istnieje taki rekord")
                }
            }
            else {
                println("takie hobby nie istnieje")
            }
        }
        else {
            println("taki profil nie istnieje")
        }
    }

    @PutMapping("editPreferences")
    fun editHobbyPreferences(@RequestBody body: InterestedHobbyDTO, response: HttpServletResponse) {
        println("edycja ")
        if (this.profileService.profileExistById(body.profileId)) {
            if(this.hobbyService.hobbyExistById(body.hobbyId)){
                val profile = this.profileService.getProfileById(body.profileId)
                val hobby = this.hobbyService.getHobbyById(body.hobbyId)
                val oldHobbyPreferences = this.interestedHobbyService.getInterestedHobbyByProfileId(profile,hobby) // .getInterestedRelationshipByProfileId(profile,relationship)
                oldHobbyPreferences.decison = body.decision
                this.interestedHobbyService.save(oldHobbyPreferences)
            } else {
                println("Nie istnieje płeć lub orientacja")
            }
        } else {
            println("taki profile nie istnieje")
        }
    }


    @PostMapping("newHobbyUser")
    fun addHobbyUser(@RequestBody body: HobbyUserDTO, response: HttpServletResponse) {
         if(this.profileService.profileExistById(body.profileId)) {
            if(this.hobbyService.hobbyExistById(body.hobbyId)){
                val profile = this.profileService.getProfileById(body.profileId)
                val hobby = this.hobbyService.getHobbyById(body.hobbyId)
                val checkHobbyUser = this.hobbyUserService.hobbyInterestedExists(profile,hobby)
                if(checkHobbyUser.isEmpty()){
                    println("nie istnieje taki rekord")
                    val userHobby = HobbyUserModel()
                    userHobby.decison = body.decision
                    userHobby.profile = profile
                    userHobby.hobby = hobby
                    this.hobbyUserService.save(userHobby)
                }
                else {
                    println("istnieje taki rekord")
                }
            }
            else {
                println("takie hobby nie istnieje")
            }
        }
        else {
            println("taki profil nie istnieje")
        }
    }


    @PutMapping("editUserHobby")
    fun editHobbyuser(@RequestBody body: HobbyUserDTO, response: HttpServletResponse) {
        println("edycja ")
        if (this.profileService.profileExistById(body.profileId)) {
            if(this.hobbyService.hobbyExistById(body.hobbyId)){
                val profile = this.profileService.getProfileById(body.profileId)
                val hobby = this.hobbyService.getHobbyById(body.hobbyId)
                val oldHobbyUser = this.hobbyUserService.getInterestedHobbyByProfileId(profile,hobby) // .getInterestedRelationshipByProfileId(profile,relationship)
                oldHobbyUser.decison = body.decision
                this.hobbyUserService.save(oldHobbyUser)
            } else {
                println("Nie istnieje płeć lub orientacja")
            }
        } else {
            println("taki profile nie istnieje")
        }
    }

}