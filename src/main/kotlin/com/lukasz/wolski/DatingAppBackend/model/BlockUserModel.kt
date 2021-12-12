package com.lukasz.wolski.DatingAppBackend.model

import javax.persistence.*

@Entity(name="Block_user")
class BlockUserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0

    @ManyToOne
    @JoinColumn(name="profile_id")
    var profileId: ProfileModel = ProfileModel()

    @ManyToOne
    @JoinColumn(name="profile_id_block")
    var profileIdBlock: ProfileModel = ProfileModel()

    /* WIELE DO WIELU
    * Z PERSON MODEL
    * */


}