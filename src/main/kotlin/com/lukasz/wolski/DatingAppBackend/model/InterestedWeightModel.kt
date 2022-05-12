package com.lukasz.wolski.DatingAppBackend.model

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*
@Entity(name="Interested_weight")
class InterestedWeightModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0
    /*
        @Column
        var user_id: Int = 0
    */
    @Column
    var weight_from: Int = 40

    @Column
    var weight_to: Int = 130

    /* WIELE DO JEDNEGO
    * Z PERSON MODEL
    * */
    @OneToOne
    @JoinColumn(name="profile_id", unique = true)
    @JsonIgnore
    var profileId: ProfileModel = ProfileModel()
}