package com.lukasz.wolski.DatingAppBackend.model

import lombok.NoArgsConstructor
import java.sql.Date
import javax.persistence.*


@NoArgsConstructor
@Entity(name = "Profile")
class ProfileModel{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0

    @Column
    var name: String = ""

    @Column
    var data_birth: Date = Date(0)

    @Column(length = 1000)
    var description: String = ""

    @Column(length = 100)
    var job: String = ""

    @Column
    var location: String = ""
/*
    @Column
    var id_gender: String = ""

    @Column
    var id_orientation: Int = 0
*/
    @Column
    var height: Int = 0

    @Column
    var weight: Double = 0.0

    @ManyToOne
    @JoinColumn(name="d_zodiac")
    var dictionaryZodiac: DictionaryZodiacModel = DictionaryZodiacModel()

    @ManyToOne
    @JoinColumn(name="d_education")
    var dictionaryEducation: DictionaryEducationModel = DictionaryEducationModel()

    @ManyToOne
    @JoinColumn(name="d_religious")
    var dictionaryReligious: DictionaryReligiousModel = DictionaryReligiousModel()

    @ManyToOne
    @JoinColumn(name="d_children")
    var dictionaryChildren: DictionaryChildrenModel = DictionaryChildrenModel()

    @ManyToOne
    @JoinColumn(name="d_alcohol")
    var dictionaryAlcohol: DictionaryAlcoholModel = DictionaryAlcoholModel()

    @ManyToOne
    @JoinColumn(name="d_cigarettes")
    var dictionaryCigarettes: DictionaryCigarettesModel = DictionaryCigarettesModel()

    @ManyToOne
    @JoinColumn(name="d_eye_color")
    var dictionaryEyeColor: DictionaryEyeColorModel = DictionaryEyeColorModel()

    @ManyToOne
    @JoinColumn(name="orienatation_id")
    var dictionaryOrientation: DictionaryOrientationModel = DictionaryOrientationModel()

    @ManyToOne
    @JoinColumn(name="gender_id")
    var dictionaryGender: DictionaryGenderModel = DictionaryGenderModel()



    @OneToOne
    @JoinColumn(name="user_id")
    var user: UserModel = UserModel()
    //var id_user: Int = 0

    /* WIELE DO WIELU
    * Z BLOCK USER MODEL
    * */

    /* WIELE DO JEDNEGO
    * Z GENDER MODEL
    * */

    /* WIELE DO JEDNEGO
    * Z HOBBY USER MODEL
    * */

    /* WIELE DO JEDNEGO
    * Z IMAGE MODEL
    * */

    /* WIELE DO JEDNEGO
    * Z INTERESTED AGE MODEL
    * */

    /* WIELE DO JEDNEGO
    * Z INTERESTED GENDER MODEL
    * */

    /* WIELE DO JEDNEGO
    * Z INTERESTED HOBBY MODEL
    * */

    /* WIELE DO JEDNEGO
    * Z INTERESTED RELATIONSHIP MODEL
    * */

    /* WIELE DO JEDNEGO
    * Z ORIENTATION MODEL
    * */


}
