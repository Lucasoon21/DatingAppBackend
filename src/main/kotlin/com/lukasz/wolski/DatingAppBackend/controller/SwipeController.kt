package com.lukasz.wolski.DatingAppBackend.controller

import com.lukasz.wolski.DatingAppBackend.dtos.ShortProfileUsersOnSwipeDTO
import com.lukasz.wolski.DatingAppBackend.model.DictionaryGenderModel
import com.lukasz.wolski.DatingAppBackend.model.ProfileModel
import com.lukasz.wolski.DatingAppBackend.services.*
import org.joda.time.LocalDate
import org.joda.time.Years
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("swipe")
class SwipeController(private val userService: UserService,
                      private val profileService: ProfileService,
                      private val matchService: MatchService,
                      private val imageUserService: ImageUserService,
                      private val swipeDecisionService: SwipeDecisionService,
                      private val interestedGenderService: InterestedGenderService,
                      private val dictionaryService: DictionaryService
                      ) {


    @RequestMapping("getLikesForMyProfile")
    fun getLikesForMyProfile(@RequestParam(value = "profile") profileId: Int): ArrayList<ShortProfileUsersOnSwipeDTO>? {
        if (this.profileService.profileExistById(profileId)) {
            val profile = profileService.getProfileById(profileId)
            val myLike = swipeDecisionService.getAllLiked(profile) //wszystkie lajki które komuś dałem
            val profileILike: ArrayList<ProfileModel>
            if(myLike!=null && myLike.size!=0){
                profileILike = myLike.map { it.userReceiver } as ArrayList<ProfileModel>
            } else {
                profileILike = ArrayList<ProfileModel>()
            }

            val likedMe = swipeDecisionService.getAllLikeMe(profile, profileILike)

            println("myLike "+myLike.size) // wszystki lajki które ja dałem
            println("profileILike "+profileILike.size) // wsszystkie profile którym dałem like
            println("likedMe "+ (likedMe?.size ?: 0))

            val returnListProfiles =  ArrayList<ShortProfileUsersOnSwipeDTO>()
            if (likedMe != null) {
                for(profiles in likedMe) {
                    val localNow: LocalDate = LocalDate.now()
                    val birthDate: LocalDate = LocalDate.fromDateFields(profiles.userGiven.date_birth)
                    val age: Years = Years.yearsBetween(birthDate, localNow)
                    val profilePhoto = imageUserService.getMainPhoto(profiles.userGiven)

                    val shortProfile = ShortProfileUsersOnSwipeDTO(
                        profiles.userGiven.id,
                        profiles.userGiven.name,
                        age.years,
                        profiles.userGiven.job,
                        profilePhoto
                    )
                    returnListProfiles.add(shortProfile)

                }
                return returnListProfiles
            }
            return null
        }
        return null;
    }




    @RequestMapping("getAllProfile")
    fun helloWorld(@RequestParam(value = "profile") profileId: Int): ArrayList<ShortProfileUsersOnSwipeDTO>? {
        if (this.profileService.profileExistById(profileId)) {
            val profile = this.profileService.getProfileById(profileId)
            val allLikesProfiles = this.swipeDecisionService.getAllLiked(profile)
            val likedProfiles:ArrayList<Int> = allLikesProfiles.map { it.userReceiver.id } as ArrayList<Int>
            likedProfiles.add (profileId)

            //Szukanie preferencji co do płci
            val genderPreferencesUser = interestedGenderService.getAllInterestedGenderByProfile(profile)
            val genderInter:ArrayList<DictionaryGenderModel>
            if(genderPreferencesUser!=null && genderPreferencesUser.size!=0){
                genderInter = genderPreferencesUser.map { it.gender } as ArrayList<DictionaryGenderModel>
                println("Znaleziono zainteresowane płcie "+genderPreferencesUser.size)
            } else {
                genderInter = dictionaryService.getAllGenderDictionary() as ArrayList<DictionaryGenderModel>
                println("Nie znaleziono zainteresowane płcie ")
            }



            val detailsProfile = this.swipeDecisionService.getAllProfiles(profile, likedProfiles, genderInter)
            println("Liczba znalezionych profili = "+ (detailsProfile?.size ?: 0))
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