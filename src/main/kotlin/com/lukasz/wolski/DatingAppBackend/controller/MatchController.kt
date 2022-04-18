package com.lukasz.wolski.DatingAppBackend.controller

import com.lukasz.wolski.DatingAppBackend.dtos.InterestedRelationshipDTO
import com.lukasz.wolski.DatingAppBackend.dtos.ProfileImageDTO
import com.lukasz.wolski.DatingAppBackend.dtos.ShortProfileUsersOnSwipeDTO
import com.lukasz.wolski.DatingAppBackend.model.MatchModel
import com.lukasz.wolski.DatingAppBackend.model.ProfileModel
import com.lukasz.wolski.DatingAppBackend.services.ImageUserService
import com.lukasz.wolski.DatingAppBackend.services.MatchService
import com.lukasz.wolski.DatingAppBackend.services.ProfileService
import org.joda.time.LocalDate
import org.joda.time.Years
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("match")
class MatchController(
    private val profileService: ProfileService,
    private val matchService: MatchService,
    private val imageUserService: ImageUserService,
) {

    @RequestMapping("getProfileRelationship")
    fun getProfileMatch(@RequestParam(value = "profile") profileId: Int): ArrayList<ShortProfileUsersOnSwipeDTO>? {
      //  fun getProfileMatch(@RequestParam(value = "profile") profileId: Int): ArrayList<ShortProfileUsersOnSwipeDTO>? {
        if (this.profileService.profileExistById(profileId)) {
            val profile = profileService.getProfileById(profileId)
            val matchList = matchService.getAllMatch(profile)

            val returnListMatches = ArrayList<ShortProfileUsersOnSwipeDTO>()
            if (matchList != null) {
                for(item in matchList) {
                    var profile = ProfileModel()
                    if(item.profileFirst.id==profileId) {
                        profile = item.profileSecond
                    } else {
                        profile = item.profileFirst
                    }

                    val localNow: LocalDate = LocalDate.now()
                    val birthDate: LocalDate = LocalDate.fromDateFields(profile.date_birth)
                    val age: Years = Years.yearsBetween(birthDate, localNow)

                    val profilePhoto = imageUserService.getMainPhoto(profile)

                    val shortInfo = ShortProfileUsersOnSwipeDTO(
                        profile.id,
                        profile.name,
                        age.years,
                        profile.job,
                        profilePhoto,
                    )
                    returnListMatches.add(shortInfo)

                }
                return returnListMatches
            }

            return null
        }
        return null;
    }

}