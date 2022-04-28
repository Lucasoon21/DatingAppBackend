package com.lukasz.wolski.DatingAppBackend.model

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*
@Entity(name="Interested_height")
class InterestedHeightModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0
    /*
        @Column
        var user_id: Int = 0
    */
    @Column
    var height_from: Int = 140

    @Column
    var height_to: Int = 200

    /* WIELE DO JEDNEGO
    * Z PERSON MODEL
    * */
    @OneToOne
    @JoinColumn(name="profile_id")
    @JsonIgnore
    var profileId: ProfileModel = ProfileModel()
}