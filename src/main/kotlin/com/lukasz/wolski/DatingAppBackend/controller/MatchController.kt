package com.lukasz.wolski.DatingAppBackend.controller

import com.lukasz.wolski.DatingAppBackend.dtos.*
import com.lukasz.wolski.DatingAppBackend.model.ProfileModel
import com.lukasz.wolski.DatingAppBackend.services.ImageUserService
import com.lukasz.wolski.DatingAppBackend.services.MatchService
import com.lukasz.wolski.DatingAppBackend.services.ProfileService
import com.lukasz.wolski.DatingAppBackend.services.SwipeDecisionService
import org.joda.time.LocalDate
import org.joda.time.Years
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("match")
class MatchController(
    private val profileService: ProfileService,
    private val matchService: MatchService,
    private val imageUserService: ImageUserService,
    private val swipeDecisionService: SwipeDecisionService,
) {

    @DeleteMapping("deleteMatch")
    fun deleteMatch(@RequestBody body: MatchDTO): ResponseEntity<String> {
        if (this.profileService.profileExistById(body.profileId) && this.profileService.profileExistById(body.selectProfileUserId)) {
            val profileFirst = profileService.getProfileById(body.profileId)
            val profileSecond = profileService.getProfileById(body.selectProfileUserId)

            val match = matchService.getMatch(profileFirst, profileSecond)
            val match2 = matchService.getMatch(profileSecond, profileFirst)
            if(match!=null) {
                matchService.deleteMatch(match.id)
                return ResponseEntity.ok().body("usunięto match\n")
            } else if(match2!=null) {
                matchService.deleteMatch(match2.id)
                return ResponseEntity.ok().body("usunięto match\n")
            } else {
                println("nie znaleziono match")
                return ResponseEntity.badRequest().body("Nie znaleziono match\n")
            }
        } else {
            println("nie znaleziono konta")
        }
        println("błąd")
        return ResponseEntity.badRequest().body("Nie usunięto match\n")
    }

    @GetMapping("getAllMatch")
    fun getAllMatch(@RequestParam(value = "profile") profileId: Int): ArrayList<ShortProfileUsersOnSwipeDTO>? {
      //  fun getProfileMatch(@RequestParam(value = "profile") profileId: Int): ArrayList<ShortProfileUsersOnSwipeDTO>? {
        if (this.profileService.profileExistById(profileId)) {
            val profile = profileService.getProfileById(profileId)
            //tu chyba nie jesy za dobrze, sprawdzic zapytanie
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
                    val birthDate: LocalDate = LocalDate.fromDateFields(profile.dateBirth)
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