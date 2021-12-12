package com.lukasz.wolski.DatingAppBackend.model

import javax.persistence.*

@Entity(name="Interested_hobby")
class InterestedHobbyModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0

/*    @Column
    var user_id: Int = 0*/




    /* WIELE DO JEDNEGO
    * Z PERSON MODEL
    * */
    @ManyToOne
    @JoinColumn(name="profile_id")
    var profile: ProfileModel = ProfileModel()

    @ManyToOne
    @JoinColumn(name="hobby_id")
    var hobby: TypeHobbyModel = TypeHobbyModel()

    @Column
    var decison: Int = 0
}