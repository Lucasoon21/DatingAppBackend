package com.lukasz.wolski.DatingAppBackend.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonManagedReference
import lombok.NoArgsConstructor
import java.sql.Date
import javax.persistence.*


@NoArgsConstructor
@Entity(name = "Profile")
 data class ProfileModel(

   @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0,

   @Column
    var name: String = "",

   @Column
   var isActive: Boolean? = true,

   @Column
    var date_birth: Date? = Date(0),

   @Column(length = 1000)
    var description: String? = "",

   @Column(length = 100)
    var job: String? = "",

   @Column
    var location: String? = "",
/*
    @Column
    var id_gender: String = ""

    @Column
    var id_orientation: Int = 0
*/
    @Column
    var height: Int? = 0,

   @Column
    var weight: Int? = 0,

   @ManyToOne(fetch=FetchType.LAZY)
   @JoinColumn(name="d_zodiac", nullable = true)
    var dictionaryZodiac: DictionaryZodiacModel? = null,

   @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="d_education", nullable = true)
    var dictionaryEducation: DictionaryEducationModel? = null,

   @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="d_religious", nullable = true)
    var dictionaryReligious: DictionaryReligiousModel? = null,

   @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="d_children", nullable = true)
    var dictionaryChildren: DictionaryChildrenModel? = null,

   @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="d_alcohol", nullable = true)
    var dictionaryAlcohol: DictionaryAlcoholModel? = null,

   @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="d_cigarettes", nullable = true)
    var dictionaryCigarettes: DictionaryCigarettesModel? = null,

   @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="d_eye_color", nullable = true)
    var dictionaryEyeColor: DictionaryEyeColorModel? = null,

   @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="orienatation_id", nullable = true)
    var dictionaryOrientation: DictionaryOrientationModel?= null,

   @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="gender_id", nullable = true)
    var dictionaryGender: DictionaryGenderModel? = null,



    @OneToOne
    @JoinColumn(name="user_id")
    @JsonIgnore
    var user: UserModel = UserModel(),

   @OneToMany(mappedBy = "profile", fetch = FetchType.LAZY, cascade = arrayOf(CascadeType.ALL))
    val images: List<ImageUserModel>? = emptyList(),

   @OneToMany(mappedBy = "profile", fetch = FetchType.LAZY, cascade = arrayOf(CascadeType.ALL))
   val userHobby: List<HobbyUserModel>? = emptyList(),

   @OneToOne(mappedBy = "profileId",fetch = FetchType.LAZY, cascade = arrayOf(CascadeType.ALL))
   val interestedAge: InterestedAgeModel? = null,

   @OneToOne(mappedBy = "profileId",fetch = FetchType.LAZY, cascade = arrayOf(CascadeType.ALL))
   val interestedHeight: InterestedHeightModel? = null,

   @OneToOne(mappedBy = "profileId",fetch = FetchType.LAZY, cascade = arrayOf(CascadeType.ALL))
   val interestedWeight: InterestedWeightModel? = null,

    @OneToMany(mappedBy = "profile", fetch = FetchType.LAZY, cascade = arrayOf(CascadeType.ALL))
    val interestedGender: List<InterestedGenderModel>? = emptyList(),

   @OneToMany(mappedBy = "profile", fetch = FetchType.LAZY, cascade = arrayOf(CascadeType.ALL))
   val interestedHobby: List<InterestedHobbyModel>? = emptyList(),

   @OneToMany(mappedBy = "profile", fetch = FetchType.LAZY, cascade = arrayOf(CascadeType.ALL))
   val interestedRelationship: List<InterestedRelationshipModel>? = emptyList(),

   @OneToMany(mappedBy = "userReceiver", fetch = FetchType.LAZY, cascade = arrayOf(CascadeType.ALL))
   val swipeDecisionReceiver: List<SwipeDecisionModel>? = emptyList(),

   @OneToMany(mappedBy = "userGiven", fetch = FetchType.LAZY, cascade = arrayOf(CascadeType.ALL))
   val swipeDecisionGiven: List<SwipeDecisionModel>? = emptyList(),

   @OneToMany(mappedBy = "profileFirst", fetch = FetchType.LAZY, cascade = arrayOf(CascadeType.ALL))
   val profileFirst: List<MatchModel>? = emptyList(),

   @OneToMany(mappedBy = "profileSecond", fetch = FetchType.LAZY, cascade = arrayOf(CascadeType.ALL))
   val profileSecond: List<MatchModel>? = emptyList(),

)
