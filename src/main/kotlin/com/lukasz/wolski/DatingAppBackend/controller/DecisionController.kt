package com.lukasz.wolski.DatingAppBackend.controller

import com.lukasz.wolski.DatingAppBackend.dtos.BlockUserDTO
import com.lukasz.wolski.DatingAppBackend.dtos.NewMatchDTO
import com.lukasz.wolski.DatingAppBackend.dtos.SwipeDTO
import com.lukasz.wolski.DatingAppBackend.model.MatchModel
import com.lukasz.wolski.DatingAppBackend.model.ProfileModel
import com.lukasz.wolski.DatingAppBackend.model.SwipeDecisionModel
import com.lukasz.wolski.DatingAppBackend.services.*
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("decision")
class DecisionController(private val profileService: ProfileService,
                         private val swipeDecisionService: SwipeDecisionService,
                         private val imageUserService: ImageUserService,
                         private val matchService: MatchService,
) {

    @PutMapping("swipeUser")
    fun swipeUser(@RequestBody body: SwipeDTO, response: HttpServletResponse): NewMatchDTO? {

        if(this.profileService.profileExistById(body.profileId) && this.profileService.profileExistById(body.selectProfileUserId) && body.profileId!=body.selectProfileUserId) {
            val profile = this.profileService.getProfileById(body.profileId)
            val selectProfile = this.profileService.getProfileById(body.selectProfileUserId)

            //ISTNIEJA OBAJ UZYTKOWNICY
            val swipe = SwipeDecisionModel()
            val checkSwipe = swipeDecisionService.swipeExists(profile, selectProfile)
            if(checkSwipe.isEmpty()){

                swipe.decision = body.decision
                swipe.userGiven = profile
                swipe.userReceiver = selectProfile
                this.swipeDecisionService.save(swipe)

                if(body.decision==1){
                    val swipe2 = SwipeDecisionModel()
                    swipe2.decision = 1
                    swipe2.userGiven = selectProfile
                    swipe2.userReceiver = profile
                    val checkSwipeIsLike = swipeDecisionService.checkMatch(swipe2)
                    println("check match $checkSwipeIsLike")

                    if(checkSwipeIsLike===true) {

                        val match = MatchModel()
                        match.date_match = Date()
                        match.profileFirst = profile
                        match.profileSecond = selectProfile
                        this.matchService.save(match)
                        val matchedProfile: ProfileModel
                        if(body.profileId==profile.id) {
                            matchedProfile = selectProfile
                        } else {
                            matchedProfile = profile
                        }

                        val profileImage = imageUserService.getMainPhoto(matchedProfile)
                        val matchReturn = NewMatchDTO(
                            matchedProfile.id,
                            matchedProfile.name,
                            profileImage
                        )
                        return matchReturn

                    }
                }
            }
            else {
                println("istnieje juz taki rekord")
            }
        }
        else {
            println("nie istnieja takie profile")
        }
        return null
    }


}