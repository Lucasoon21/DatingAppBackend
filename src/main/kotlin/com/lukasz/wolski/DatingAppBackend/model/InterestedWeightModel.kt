package com.lukasz.wolski.DatingAppBackend.model

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
    var weight_from: Int = 0

    @Column
    var weight_to: Int = 0

    /* WIELE DO JEDNEGO
    * Z PERSON MODEL
    * */
    @OneToOne
    @JoinColumn(name="profile_id")
    var profileId: ProfileModel = ProfileModel()
}