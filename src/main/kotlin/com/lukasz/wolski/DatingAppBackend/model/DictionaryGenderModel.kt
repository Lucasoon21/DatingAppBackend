package com.lukasz.wolski.DatingAppBackend.model

import javax.persistence.*

@Entity(name = "D_Gender")
class DictionaryGenderModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0

    @Column
    var name: String = ""

    /* WIELE DO JEDNEGO
    * Z PERSON MODEL
    * */
}