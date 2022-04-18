package com.lukasz.wolski.DatingAppBackend.controller

import com.lukasz.wolski.DatingAppBackend.dtos.ProfileImageDTO
import com.lukasz.wolski.DatingAppBackend.dtos.ShortProfileUsersOnSwipeDTO
import com.lukasz.wolski.DatingAppBackend.model.ProfileModel
import com.lukasz.wolski.DatingAppBackend.services.*
import org.joda.time.LocalDate
import org.joda.time.Years
import org.modelmapper.ModelMapper
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("swipe")
class SwipeController(private val userService: UserService,
                      private val profileService: ProfileService,
                      private val matchService: MatchService,
                      private val imageUserService: ImageUserService,
                      private val swipeDecisionService: SwipeDecisionService
                      ) {

    @RequestMapping("getAllProfile")
    fun helloWorld(@RequestParam(value = "profile") profileId: Int): ArrayList<ShortProfileUsersOnSwipeDTO>? {
        if (this.profileService.profileExistById(profileId)) {
            val profile = this.profileService.getProfileById(profileId)
            val detailsProfile = this.swipeDecisionService.getAllProfiles(profile)
            if(detailsProfile != null) {
                val returnListProfiles =  ArrayList<ShortProfileUsersOnSwipeDTO>()
                for (profiles in detailsProfile) {
                    val localNow: LocalDate = LocalDate.now()
                    val birthDate: LocalDate = LocalDate.fromDateFields(profiles.date_birth)
                    val age: Years = Years.yearsBetween(birthDate, localNow)

                    val profilePhoto = imageUserService.getMainPhoto(profiles)
                    val shortProfile = ShortProfileUsersOnSwipeDTO(
                        profiles.id,
                        profiles.name,
                        age.years,
                        profiles.job,
                        profilePhoto
                    )
                    returnListProfiles.add(shortProfile)
                }
                return returnListProfiles
            }

        }
        return null
    }

}