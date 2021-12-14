package com.lukasz.wolski.DatingAppBackend.controller

import com.lukasz.wolski.DatingAppBackend.dtos.ProfileDTO
import com.lukasz.wolski.DatingAppBackend.dtos.RegisterDTO
import com.lukasz.wolski.DatingAppBackend.dtos.RegisterDetailsDTO
import com.lukasz.wolski.DatingAppBackend.model.ProfileModel
import com.lukasz.wolski.DatingAppBackend.model.TypeGenderModel
import com.lukasz.wolski.DatingAppBackend.model.TypeOrientationModel
import com.lukasz.wolski.DatingAppBackend.services.GenderService
import com.lukasz.wolski.DatingAppBackend.services.OrientationService
import com.lukasz.wolski.DatingAppBackend.services.ProfileService
import com.lukasz.wolski.DatingAppBackend.services.UserService
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("profile")
class ProfileUserController(
    private val userService: UserService,
    private val profileService: ProfileService,
    private val genderService: GenderService,
    private val orientationService: OrientationService,
) {

    @PutMapping("edit")
    fun editProfile(@RequestBody body: ProfileDTO, response: HttpServletResponse) {
        println("edycja profilu")
        if (this.profileService.profileExistById(body.profileId)) {
            if (this.genderService.genderExistById(body.genderId) && this.orientationService.orientationExistsById(body.orientationId)) {
                val gender = this.genderService.getGender(body.genderId)
                val orientation = this.orientationService.getOrientation(body.orientationId)
                val oldProfile = this.profileService.getProfileById(body.profileId)
                if (gender != null) {
                    oldProfile.typeGender = gender
                }
                if (orientation != null) {
                    oldProfile.typeOrientation = orientation
                }
                oldProfile.description = body.description
                this.profileService.save(oldProfile)
            } else {
                println("Nie istnieje płeć lub orientacja")
            }
        } else {
            println("taki profile nie istnieje")
        }
    }
}