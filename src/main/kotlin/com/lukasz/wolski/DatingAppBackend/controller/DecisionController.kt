package com.lukasz.wolski.DatingAppBackend.controller

import com.lukasz.wolski.DatingAppBackend.dtos.BlockUserDTO
import com.lukasz.wolski.DatingAppBackend.dtos.SwipeDTO
import com.lukasz.wolski.DatingAppBackend.model.BlockUserModel
import com.lukasz.wolski.DatingAppBackend.model.MatchModel
import com.lukasz.wolski.DatingAppBackend.model.SwipeDecisionModel
import com.lukasz.wolski.DatingAppBackend.services.BlockUserService
import com.lukasz.wolski.DatingAppBackend.services.MatchService
import com.lukasz.wolski.DatingAppBackend.services.ProfileService
import com.lukasz.wolski.DatingAppBackend.services.SwipeDecisionService
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("decision")
class DecisionController(private val profileService: ProfileService,
                        private val blockUserService: BlockUserService,
                         private val swipeDecisionService: SwipeDecisionService,
                         private val matchService: MatchService,
) {

    @PostMapping("blockUser")
    fun blockUser(@RequestBody body: BlockUserDTO,response: HttpServletResponse){

        if(this.profileService.profileExistById(body.user) && this.profileService.profileExistById(body.userBlocked) && body.user!=body.userBlocked) {
            //ISTNIEJA OBAJ UZYTKOWNICY
            val block = BlockUserModel()
            val profile = this.profileService.getProfileById(body.user)
            val profileBlock= this.profileService.getProfileById(body.userBlocked)
            val checkBlock = blockUserService.blockExists(profile, profileBlock)
            if(checkBlock.isEmpty()){

                block.profileId = profile
                block.profileIdBlock = profileBlock
                this.blockUserService.save(block)
            }
            else{
                println("istnieje juz taki rekord")
            }
            println("istnieja takie profile")
        }
        else {
            println("nie istnieja takie profile")
        }
    }

    @PutMapping("swipeUser")
    fun swipeUser(@RequestBody body: SwipeDTO, response: HttpServletResponse){

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

                    }
                }
            }
            else{
                println("istnieje juz taki rekord")
            }
            println("istnieja takie profile")
        }
        else {
            println("nie istnieja takie profile")
        }
    }

    @PutMapping("removePair")
    fun removePair(@RequestBody body: SwipeDTO, response: HttpServletResponse) {

        if (this.profileService.profileExistById(body.profileId) && this.profileService.profileExistById(body.selectProfileUserId)) {
                val profile = this.profileService.getProfileById(body.profileId)
                val profileSelect = this.profileService.getProfileById(body.selectProfileUserId)
                val pair = this.swipeDecisionService.getPair(profile,profileSelect)

                 pair.decision = body.decision
                this.swipeDecisionService.save(pair)

        } else {
            println("taki profile nie istnieje")
        }
    }
}