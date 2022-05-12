package com.lukasz.wolski.DatingAppBackend.model
import com.fasterxml.jackson.annotation.JsonIgnore
import lombok.NoArgsConstructor
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import javax.persistence.*
@NoArgsConstructor
@Entity(name = "UserDetails")
class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0

    @Column(unique = true)
    var email = ""

    @Column
    var password = ""
        @JsonIgnore
        get() = field
        set(value) {
            val passwordEncoder = BCryptPasswordEncoder()
            field = passwordEncoder.encode(value)
        }

    @Column
    var isActive = true
/*
    @OneToOne(mappedBy = "user",fetch = FetchType.LAZY, cascade = arrayOf(CascadeType.ALL))
    @Column(unique = true)
    val profileUser: ProfileModel = ProfileModel()
*/
    fun comparePassword(password: String): Boolean {
        return BCryptPasswordEncoder().matches(password, this.password)
    }
}
