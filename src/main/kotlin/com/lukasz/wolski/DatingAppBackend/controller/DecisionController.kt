package com.lukasz.wolski.DatingAppBackend.controller

import com.lukasz.wolski.DatingAppBackend.dtos.BlockUserDTO
import com.lukasz.wolski.DatingAppBackend.model.BlockUserModel
import com.lukasz.wolski.DatingAppBackend.services.BlockUserService
import com.lukasz.wolski.DatingAppBackend.services.ProfileService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("decision")
class DecisionController(private val profileService: ProfileService,
                        private val blockUserService: BlockUserService
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
}