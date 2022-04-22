package com.lukasz.wolski.DatingAppBackend.model

import javax.persistence.*

@Entity(name="localization")
class LocalizationModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0

    @Column
    var latitude: Double = 0.0

    @Column
    var longitude: Double = 0.0
}