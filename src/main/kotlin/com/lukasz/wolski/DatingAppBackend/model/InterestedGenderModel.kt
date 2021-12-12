package com.lukasz.wolski.DatingAppBackend.model

import javax.persistence.*

@Entity(name="Interested_gender")
class InterestedGenderModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0

    @Column
    var decision: Int = 0

/*    @Column
    var id_gender: Int = 0*/

    /* WIELE DO JEDNEGO
    * Z PERSON MODEL
    * */
    @ManyToOne
    @JoinColumn(name="profile_id")
    var profile: ProfileModel = ProfileModel()

    @ManyToOne
    @JoinColumn(name="gender_id")
    var gender: TypeGenderModel = TypeGenderModel()
}