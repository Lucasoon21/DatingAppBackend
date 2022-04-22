package com.lukasz.wolski.DatingAppBackend.model

import java.util.*
import javax.persistence.*

@Entity(name="Match_profiles")
class MatchModel{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0

    @ManyToOne
    @JoinColumn(name="profile_first")
    var profileFirst: ProfileModel = ProfileModel()

    @ManyToOne
    @JoinColumn(name="profile_second")
    var profileSecond: ProfileModel = ProfileModel()

    @Column
    var date_match: Date = Date()

}
