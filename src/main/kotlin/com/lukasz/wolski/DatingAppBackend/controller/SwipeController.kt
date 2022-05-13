package com.lukasz.wolski.DatingAppBackend.controller

import com.lukasz.wolski.DatingAppBackend.dtos.ShortProfileUsersOnSwipeDTO
import com.lukasz.wolski.DatingAppBackend.model.DictionaryGenderModel
import com.lukasz.wolski.DatingAppBackend.model.DictionaryHobbyModel
import com.lukasz.wolski.DatingAppBackend.model.ProfileModel
import com.lukasz.wolski.DatingAppBackend.services.*
import org.joda.time.LocalDate
import org.joda.time.Years
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import kotlin.collections.ArrayList

@RestController
@RequestMapping("swipe")
class SwipeController(private val userService: UserService,
                      private val profileService: ProfileService,
                      private val matchService: MatchService,
                      private val imageUserService: ImageUserService,
                      private val swipeDecisionService: SwipeDecisionService,
                      private val interestedGenderService: InterestedGenderService,
                      private val interestedHobbyService: InterestedHobbyService,
                      private val hobbyUserService: HobbyUserService,
                      private val dictionaryService: DictionaryService
                      ) {


    @GetMapping("getLikesForMyProfile")
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


            val returnListProfiles =  ArrayList<ShortProfileUsersOnSwipeDTO>()
            if (likedMe != null) {
                for(profiles in likedMe) {
                    val localNow: LocalDate = LocalDate.now()
                    val birthDate: LocalDate = LocalDate.fromDateFields(profiles.userGiven.dateBirth)
                    val age: Years = Years.yearsBetween(birthDate, localNow)
                    val profilePhoto = imageUserService.getMainPhoto(profiles.userGiven)

                    val shortProfile = ShortProfileUsersOnSwipeDTO(
                        profiles.userGiven.id,
                        profiles.userGiven.name,
                        age.years,
                        profiles.userGiven.location,
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

    @GetMapping("getAllProfile")
    fun getAllProfile(@RequestParam(value = "profile") profileId: Int): ArrayList<ShortProfileUsersOnSwipeDTO>? {
        if (this.profileService.profileExistById(profileId)) {

            val profile = this.profileService.getProfileById(profileId)
            val allLikesProfiles = this.swipeDecisionService.getAllLiked(profile)
            val likedProfiles:ArrayList<Int> = allLikesProfiles.map { it.userReceiver.id } as ArrayList<Int>
            likedProfiles.add (profileId) //lajkowane profile nie będą wyświetlane. Do listy dodany jest też nasz profil

            //Szukanie preferencji co do płci
            val genderPreferencesUser = interestedGenderService.getAllInterestedGenderByProfile(profile)
            val genderInter:ArrayList<DictionaryGenderModel>
            if(genderPreferencesUser!=null && genderPreferencesUser.size!=0){
                //jesli uzytkownik podal swoje preferencje to pobierz liste tylko zainteresowanych plci
                genderInter = genderPreferencesUser.map { it.gender } as ArrayList<DictionaryGenderModel>
            } else { //jesli uzytkownik nie podal preferencji co do płci to wyswietl uzytkowników wszystkich płci
                genderInter = dictionaryService.getAllGenderDictionary() as ArrayList<DictionaryGenderModel>
            }

            //szukanie po hobby
            val hobbyPreferencesUser = interestedHobbyService.getAllOnlyInterestedHobbyByProfile(profile)
            var hobbyInter: ArrayList<DictionaryHobbyModel> = ArrayList()
            //jesli uzytkownik ma wybrane jakies hobby to wybierz te które zaznaczył
            var userTooInterstedHobby: ArrayList<Int> = ArrayList()
            if(hobbyPreferencesUser!=null && hobbyPreferencesUser.size!=0) {
                hobbyInter = hobbyPreferencesUser.map { it.hobby } as ArrayList<DictionaryHobbyModel>
                val profileWhoInterstedToo = hobbyUserService.getAllProfileWhoInterstedToo(hobbyInter)
                if(profileWhoInterstedToo!=null && profileWhoInterstedToo.size!=0) {
                    userTooInterstedHobby = profileWhoInterstedToo.map { it.profile!!.id } as ArrayList<Int>
                }
            }


            val searchProfile: List<ProfileModel>?
            if(hobbyPreferencesUser!=null && hobbyPreferencesUser.size!=0) {
                searchProfile = this.swipeDecisionService.getAllProfiles(profile, likedProfiles, genderInter, userTooInterstedHobby)
            } else {
                searchProfile = this.swipeDecisionService.getAllProfiles(profile, likedProfiles, genderInter)
            }
            if(searchProfile != null) {
                val returnListProfiles =  ArrayList<ShortProfileUsersOnSwipeDTO>()
                for (profiles in searchProfile) {
                    val localNow: LocalDate = LocalDate.now()
                    val birthDate: LocalDate = LocalDate.fromDateFields(profiles.dateBirth)
                    val age: Years = Years.yearsBetween(birthDate, localNow)
                    val profilePhoto = imageUserService.getMainPhoto(profiles)
                    val shortProfile = ShortProfileUsersOnSwipeDTO(
                        profiles.id,
                        profiles.name,
                        age.years,
                        profiles.location,
                        profilePhoto
                    )
                    returnListProfiles.add(shortProfile)
                }
                returnListProfiles.shuffle()
                return returnListProfiles
            }
        }
        return null
    }

}