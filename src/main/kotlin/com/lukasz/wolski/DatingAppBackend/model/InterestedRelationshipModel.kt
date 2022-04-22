package com.lukasz.wolski.DatingAppBackend.model

import javax.persistence.*

@Entity(name="Interested_relationship")
class InterestedRelationshipModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0

    @ManyToOne(cascade = arrayOf(CascadeType.ALL))
    @JoinColumn(name="profile_id")
    var profile: ProfileModel = ProfileModel()

    @ManyToOne()
    @JoinColumn(name="relationship_id")
    var relationship:DictionaryRelationshipModel = DictionaryRelationshipModel()

    @Column
    var decison: Int = 0
    /* WIELE DO JEDNEGO
    * Z PERSON MODEL
    * */

}