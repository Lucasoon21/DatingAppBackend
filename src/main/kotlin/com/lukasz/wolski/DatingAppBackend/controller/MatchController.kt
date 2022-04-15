package com.lukasz.wolski.DatingAppBackend.controller

import com.lukasz.wolski.DatingAppBackend.model.ProfileModel
import com.lukasz.wolski.DatingAppBackend.services.MatchService
import com.lukasz.wolski.DatingAppBackend.services.ProfileService
import com.lukasz.wolski.DatingAppBackend.services.UserService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("match")
class MatchController(private val userService: UserService,
                      private val profileService: ProfileService,
                        private val matchService: MatchService,
                      ) {

    @RequestMapping("getAllProfile")
    fun helloWorld(@RequestParam(value = "profile") profileId: Int): List<ProfileModel>? {
        if (this.profileService.profileExistById(profileId)) {
            val profile = this.profileService.getProfileById(profileId)
            return this.matchService.getAllProfiles(profile)

        }
        return null
    }

}