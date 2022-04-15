package com.lukasz.wolski.DatingAppBackend.model

import com.fasterxml.jackson.annotation.JsonManagedReference
import javax.persistence.*

//obrazek

@Entity(name="test_model")
class TestModel{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0


    @ManyToOne()
    @JoinColumn(name = "tescik_id")
    var costam: TestModel2? = null
}