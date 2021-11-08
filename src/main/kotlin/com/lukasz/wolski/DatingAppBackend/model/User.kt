package com.lukasz.wolski.DatingAppBackend.model


import javax.persistence.*

@Entity
class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0

    @Column(unique = true)
    var email: String = ""

    @Column
    var password: String = ""

    @Column
    var active: Boolean = true
}