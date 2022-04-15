package com.lukasz.wolski.DatingAppBackend.model

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity(name="Hobby_user")
class HobbyUserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0
/*
    @OneToOne
    @JsonIgnore
    @JoinColumn(name="profile_id")
    var profile: ProfileModel = ProfileModel()
*/
    @OneToOne
    @JoinColumn(name="hobby_id")
    var hobby: DictionaryHobbyModel = DictionaryHobbyModel()

    @Column
    var decison: Int = 0

    @ManyToOne
    @JoinColumn(name="profile_id")
    @JsonIgnore
    var profile: ProfileModel? = null
    /* WIELE DO JEDNEGO
    * Z PERSON MODEL
    * */
}