package com.lukasz.wolski.DatingAppBackend.controller

import com.lukasz.wolski.DatingAppBackend.dtos.BlockUserDTO
import com.lukasz.wolski.DatingAppBackend.dtos.SwipeDTO
import com.lukasz.wolski.DatingAppBackend.model.BlockUserModel
import com.lukasz.wolski.DatingAppBackend.model.SwipeDecisionModel
import com.lukasz.wolski.DatingAppBackend.services.BlockUserService
import com.lukasz.wolski.DatingAppBackend.services.ProfileService
import com.lukasz.wolski.DatingAppBackend.services.SwipeDecisionService
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("decision")
class DecisionController(private val profileService: ProfileService,
                        private val blockUserService: BlockUserService,
                         private val swipeDecisionService: SwipeDecisionService,
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
                println("nie istnieje taki rekord")
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

    @PostMapping("swipeUser")
    fun swipeUser(@RequestBody body: SwipeDTO, response: HttpServletResponse){

        if(this.profileService.profileExistById(body.profileId) && this.profileService.profileExistById(body.selectProfileUserId) && body.profileId!=body.selectProfileUserId) {
            //ISTNIEJA OBAJ UZYTKOWNICY
            val swipe = SwipeDecisionModel()
            val profile = this.profileService.getProfileById(body.profileId)
            val selectProfile = this.profileService.getProfileById(body.selectProfileUserId)
            val checkSwipe = swipeDecisionService.swipeExists(profile, selectProfile)
            if(checkSwipe.isEmpty()){
                println("nie istnieje taki rekord")
                swipe.userGiven = profile
                swipe.userReceiver = selectProfile
                this.swipeDecisionService.save(swipe)
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
        println("edycja ")
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