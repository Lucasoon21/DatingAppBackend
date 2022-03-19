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

    @Column
    var description: String = ""

    @Column
    var location: String = ""
/*
    @Column
    var id_gender: String = ""

    @Column
    var id_orientation: Int = 0
*/
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
