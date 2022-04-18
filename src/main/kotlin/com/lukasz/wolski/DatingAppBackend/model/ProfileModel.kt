package com.lukasz.wolski.DatingAppBackend.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonManagedReference
import lombok.NoArgsConstructor
import java.sql.Date
import javax.persistence.*


@NoArgsConstructor
@Entity(name = "Profile")
 class ProfileModel(

   @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0,

   @Column
    var name: String = "",

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

   @ManyToOne
    @JoinColumn(name="d_zodiac")
    var dictionaryZodiac: DictionaryZodiacModel? = DictionaryZodiacModel(),

   @ManyToOne
    @JoinColumn(name="d_education")
    var dictionaryEducation: DictionaryEducationModel? = DictionaryEducationModel(),

   @ManyToOne
    @JoinColumn(name="d_religious")
    var dictionaryReligious: DictionaryReligiousModel? = DictionaryReligiousModel(),

   @ManyToOne
    @JoinColumn(name="d_children")
    var dictionaryChildren: DictionaryChildrenModel? = DictionaryChildrenModel(),

   @ManyToOne
    @JoinColumn(name="d_alcohol")
    var dictionaryAlcohol: DictionaryAlcoholModel? = DictionaryAlcoholModel(),

   @ManyToOne
    @JoinColumn(name="d_cigarettes")
    var dictionaryCigarettes: DictionaryCigarettesModel? = DictionaryCigarettesModel(),

   @ManyToOne
    @JoinColumn(name="d_eye_color")
    var dictionaryEyeColor: DictionaryEyeColorModel? = DictionaryEyeColorModel(),

   @ManyToOne
    @JoinColumn(name="orienatation_id")
    var dictionaryOrientation: DictionaryOrientationModel?= DictionaryOrientationModel(),

   @ManyToOne
    @JoinColumn(name="gender_id")
    var dictionaryGender: DictionaryGenderModel? = DictionaryGenderModel(),



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
   val interestedWeight: InterestedWeightModel? = null
)
