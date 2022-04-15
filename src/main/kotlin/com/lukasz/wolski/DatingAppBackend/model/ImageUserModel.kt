package com.lukasz.wolski.DatingAppBackend.model

import com.fasterxml.jackson.annotation.JsonIgnore
import java.util.*
import javax.persistence.*

@Entity(name="Image")
class ImageUserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0

    @Column
    var imageLink: String =""

    @Column
    var deleteHashImgur: String =""

    @Column
    var idImgur: String =""

    @Column
    var createDate: Date = Date()
/*
    @Column
    var id_user: Int = 0
*/
    @ManyToOne
    @JoinColumn(name="profile_id")
    @JsonIgnore
    var profile: ProfileModel? = null
    /* WIELE DO JEDNEGO
    * Z PERSON MODEL
    * */
}