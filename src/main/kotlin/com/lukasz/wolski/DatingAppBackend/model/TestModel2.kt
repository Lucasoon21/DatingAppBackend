package com.lukasz.wolski.DatingAppBackend.model

import com.fasterxml.jackson.annotation.JsonManagedReference
import javax.persistence.*

//profileId

@Entity(name="test_model2")
class TestModel2 (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
     var id: Int = 0,

   // @OneToMany(mappedBy = "costam", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @OneToMany(mappedBy = "costam", fetch = FetchType.LAZY, cascade = arrayOf(CascadeType.ALL))
    val tescik: List<TestModel>?
)