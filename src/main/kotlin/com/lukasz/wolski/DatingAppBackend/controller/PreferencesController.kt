package com.lukasz.wolski.DatingAppBackend.controller

import com.lukasz.wolski.DatingAppBackend.services.*
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("preferences")
class PreferencesController(private val interestedAgeService: InterestedAgeService,
                            private val profileService: ProfileService,
                            private val genderService: GenderService,
                            private val interestedGenderService: InterestedGenderService,
                            private val hobbyService: HobbyService,
                            private val interestedHobbyService: InterestedHobbyService,
                            private val relationshipService: RelationshipService,
                            private val interestedRelationshipService: InterestedRelationshipService,
                            ){











}