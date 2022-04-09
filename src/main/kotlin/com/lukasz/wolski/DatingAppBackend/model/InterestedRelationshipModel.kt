package com.lukasz.wolski.DatingAppBackend.model

import javax.persistence.*

@Entity(name="Interested_relationship")
class InterestedRelationshipModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0

    @OneToOne
    @JoinColumn(name="profile_id")
    var profile: ProfileModel = ProfileModel()

    @OneToOne
    @JoinColumn(name="relationship_id")
    var relationship:DictionaryRelationshipModel = DictionaryRelationshipModel()

    @Column
    var decison: Int = 0
    /* WIELE DO JEDNEGO
    * Z PERSON MODEL
    * */

}