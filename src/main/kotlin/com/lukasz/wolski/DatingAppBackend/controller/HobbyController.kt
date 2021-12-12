package com.lukasz.wolski.DatingAppBackend.controller

import com.lukasz.wolski.DatingAppBackend.dtos.InterestedHobbyDTO
import com.lukasz.wolski.DatingAppBackend.dtos.HobbyUserDTO
import com.lukasz.wolski.DatingAppBackend.model.HobbyUserModel
import com.lukasz.wolski.DatingAppBackend.model.InterestedHobbyModel
import com.lukasz.wolski.DatingAppBackend.services.*
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
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


    @PostMapping("newHobbyUserPreferences")
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

}