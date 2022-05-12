package com.lukasz.wolski.DatingAppBackend.model

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity(name="Interested_age")
class InterestedAgeModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0
/*
    @Column
    var user_id: Int = 0
*/
    @Column
    var age_from: Int = 18

    @Column
    var age_to: Int = 60

    /* WIELE DO JEDNEGO
    * Z PERSON MODEL
    * */
    @OneToOne
    @JoinColumn(name="profile_id",unique = true)
    @JsonIgnore
    var profileId: ProfileModel = ProfileModel()



}