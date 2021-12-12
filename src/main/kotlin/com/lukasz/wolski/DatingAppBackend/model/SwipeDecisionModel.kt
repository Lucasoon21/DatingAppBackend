package com.lukasz.wolski.DatingAppBackend.model

import javax.persistence.*

@Entity(name="Swipe_decision")
class SwipeDecisionModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0
/*
    @Column
    var user_id_given: Int = 0

    @Column
    var user_id_reciever: Int = 0
*/
    @ManyToOne
    @JoinColumn(name="user_id_given")
    var user_id_given: ProfileModel = ProfileModel()

    @ManyToOne
    @JoinColumn(name="user_id_reciever")
    var user_id_reciever: ProfileModel = ProfileModel()


    @Column
    var decison: Int = 0

}