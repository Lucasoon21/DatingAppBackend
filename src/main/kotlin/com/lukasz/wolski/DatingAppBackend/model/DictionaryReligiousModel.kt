package com.lukasz.wolski.DatingAppBackend.model

import javax.persistence.*

@Entity(name="D_religious")
class DictionaryReligiousModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0

    @Column
    var name: String = ""
}