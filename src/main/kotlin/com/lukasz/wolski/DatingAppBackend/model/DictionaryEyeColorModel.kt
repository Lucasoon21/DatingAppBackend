package com.lukasz.wolski.DatingAppBackend.model

import javax.persistence.*

@Entity(name="D_eye_color")
class DictionaryEyeColorModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0

    @Column
    var name: String = ""
}