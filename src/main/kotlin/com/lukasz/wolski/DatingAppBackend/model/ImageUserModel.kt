package com.lukasz.wolski.DatingAppBackend.model

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
    var profile: ProfileModel = ProfileModel()
    /* WIELE DO JEDNEGO
    * Z PERSON MODEL
    * */
}