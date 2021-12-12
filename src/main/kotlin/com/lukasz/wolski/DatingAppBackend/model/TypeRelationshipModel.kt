package com.lukasz.wolski.DatingAppBackend.model

import javax.persistence.*

@Entity(name="D_Relationship_type")
class TypeRelationshipModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0

    @Column
    var name: String = ""
}