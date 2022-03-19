package com.lukasz.wolski.DatingAppBackend.model

import javax.persistence.*

@Entity(name="Hobby_user")
class HobbyUserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0
/*
    @Column
    var user_id: Int = 0

    @Column
    var hobby_id: Int = 0
*/
    @OneToOne
    @JoinColumn(name="profile_id")
    var profile: ProfileModel = ProfileModel()

    @OneToOne
    @JoinColumn(name="hobby_id")
    var hobby: DictionaryHobbyModel = DictionaryHobbyModel()

    @Column
    var decison: Int = 0
    /* WIELE DO JEDNEGO
    * Z PERSON MODEL
    * */
}