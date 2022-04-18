package com.lukasz.wolski.DatingAppBackend.controller

import com.lukasz.wolski.DatingAppBackend.dtos.InterestedGenderDTO
import com.lukasz.wolski.DatingAppBackend.dtos.InterestedRelationshipDTO
import com.lukasz.wolski.DatingAppBackend.model.InterestedRelationshipModel
import com.lukasz.wolski.DatingAppBackend.services.*
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("relationship")
class RelationshipController(private val profileService: ProfileService,
                             private val relationshipService: RelationshipService,
                             private val interestedRelationshipService: InterestedRelationshipService,
) {

    @PostMapping("newRelationshipPreferences")
    fun addRelationshipPreferences(@RequestBody body: InterestedRelationshipDTO, response: HttpServletResponse) {
        if(this.profileService.profileExistById(body.profileId)) {
            if(this.relationshipService.relationshipExistById(body.relationshipId)){
                val profile = this.profileService.getProfileById(body.profileId)
                val relationship = this.relationshipService.getRelationshipById(body.relationshipId)
                val checkRelationshipPreferences = this.interestedRelationshipService.relationshipPreferencesExists(profile,relationship)
                if(checkRelationshipPreferences.isEmpty()){

                    val interestedRelationship = InterestedRelationshipModel()
                    interestedRelationship.decison = body.decision
                    interestedRelationship.profile = profile
                    interestedRelationship.relationship = relationship
                    this.interestedRelationshipService.save(interestedRelationship)
                }
                else {
                    println("istnieje taki rekord")
                }
            }
            else {
                println("taka relacja nie istnieje")
            }
        }
        else {
            println("taki profil nie istnieje")
        }
    }


    @PutMapping("edit")
    fun editRelationshipPreferences(@RequestBody body: InterestedRelationshipDTO, response: HttpServletResponse) {
        println("edycja ")
        if (this.profileService.profileExistById(body.profileId)) {
            if(this.relationshipService.relationshipExistById(body.relationshipId)){
                val profile = this.profileService.getProfileById(body.profileId)
                val relationship = this.relationshipService.getRelationshipById(body.relationshipId)
                val oldRelationshipInterested = this.interestedRelationshipService.getInterestedRelationshipByProfileId(profile,relationship)
                oldRelationshipInterested.decison = body.decision
                this.interestedRelationshipService.save(oldRelationshipInterested)
            } else {
                println("Nie istnieje płeć lub orientacja")
            }
        } else {
            println("taki profile nie istnieje")
        }
    }
}