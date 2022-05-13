package com.lukasz.wolski.DatingAppBackend.controller

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.fasterxml.jackson.databind.ObjectMapper
import com.lukasz.wolski.DatingAppBackend.dtos.LoginDTO
import com.lukasz.wolski.DatingAppBackend.dtos.RegisterDTO
import com.lukasz.wolski.DatingAppBackend.dtos.RegisterDetailsDTO
import com.lukasz.wolski.DatingAppBackend.model.DictionaryGenderModel
import com.lukasz.wolski.DatingAppBackend.model.DictionaryOrientationModel
import com.lukasz.wolski.DatingAppBackend.model.ProfileModel
import com.lukasz.wolski.DatingAppBackend.model.UserModel
import com.lukasz.wolski.DatingAppBackend.services.GenderService
import com.lukasz.wolski.DatingAppBackend.services.OrientationService
import com.lukasz.wolski.DatingAppBackend.services.ProfileService
import com.lukasz.wolski.DatingAppBackend.services.UserService
import org.joda.time.LocalDate
import org.joda.time.Years
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import java.util.regex.Pattern
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@RestController
@RequestMapping("auth")
class AuthController(private val userService: UserService,
                     private val profileService: ProfileService,
                     private val orientationService: OrientationService,
                     private val genderService: GenderService)
{

    val EMAIL_ADDRESS_PATTERN: Pattern = Pattern.compile("^[a-zA-Z0-9.!#\$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*\$", Pattern.CASE_INSENSITIVE);
    val PASSWORD_PATTERH: Pattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@\$!%*#?&])[A-Za-z\\d@\$!%*#?&]{6,20}\$") //jedna litera, jedna mała liczba, jedna duża liczba, jeden znak specjalny, od 6 do 20 znaków
    val NAME_PATTERH: Pattern = Pattern.compile("^[A-Za-z\\s]{2,30}\$") //litery i spacje, od 2 do 20 znaków
    fun isValidEmail(str: String): Boolean{
        return EMAIL_ADDRESS_PATTERN.matcher(str).matches()
    }

    fun isValidPassword(str: String): Boolean{
        return PASSWORD_PATTERH.matcher(str).matches()
    }

    fun isValidName(str: String): Boolean{
        return NAME_PATTERH.matcher(str).matches()
    }

    @PostMapping("register")
    fun register(@RequestBody body: RegisterDTO, response: HttpServletResponse) {
        val email = body.email.lowercase()
        val res: MutableMap<String, String> = HashMap()
        res["isError"] = "NO"
        res["emailExists"] = "NO"
        res["errorValidate"] = "NO"
        if(isValidEmail(email) && isValidPassword(body.password) && isValidPassword(body.confirmPassword) && body.confirmPassword == body.password && isValidName(body.name)){
            val user = UserModel()
            user.email = email.lowercase()
            user.password = body.password
            user.isActive = true
            if(this.userService.emailExists(user.email)!=true){

                val gender = genderService.getGender(body.gender)
                val orientation = orientationService.getOrientation(body.orientation)
                if(gender != null && orientation!=null){
                    this.userService.save(user)
                    saveProfileUser(user, body, gender, orientation, body.email)
                    return response.setStatus(HttpServletResponse.SC_ACCEPTED)
                } else {
                    res["isError"] = "YES"
                    response.contentType = APPLICATION_JSON_VALUE
                    ObjectMapper().writeValue(response.outputStream, res)
                    println("Nie istnieje taka płeć lub orientacja"+body.gender+" "+body.orientation)
                }
            }
            else{
                res["isError"] = "YES"
                res["emailExists"] = "YES"
                response.contentType = APPLICATION_JSON_VALUE
                ObjectMapper().writeValue(response.outputStream, res)
                println("Istnieje taki mail")
                return  response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE)
            }
        } else {
            res["isError"] = "YES"
            res["errorValidate"] = "YES"
            response.contentType = APPLICATION_JSON_VALUE
            ObjectMapper().writeValue(response.outputStream, res)
            println("Dane nieprawidłowe, nie przechodzi walidacji")
            return  response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE)
        }
    }

    fun saveProfileUser(user: UserModel, body: RegisterDTO,gender: DictionaryGenderModel?,orientation: DictionaryOrientationModel?,email: String) {
        val user2 = userService.findByEmail(email)
        if (user2 != null) {
            println("istnieje mail")
            val profile = ProfileModel()
            profile.isActive = true
            profile.user = user2

            profile.name=body.name
            profile.dateBirth = body.dateBirth
            profile.dictionaryGender = gender
            profile.dictionaryOrientation = orientation

            this.profileService.save(profile)
        } else {
            println("nie istnieje mail")
        }
    }

    @PostMapping("registerDetails")
    fun registerDetails(@RequestBody body: RegisterDetailsDTO, response: HttpServletResponse) {

        val profile = ProfileModel()
        profile.dateBirth = body.dateBirth
        profile.name = body.name

        val user = userService.getUser(body.email)
        val gender = genderService.getGender(body.gender)
//        val gender = genderService.getGender(body.gender)
        val orientation = orientationService.getOrientation(body.orientation)
        if(user != null){
            if(this.profileService.userExist(user)!=true){
                if(gender != null){
                    if(orientation != null) {
                        profile.user = user
                        profile.dictionaryGender = gender
                        profile.dictionaryOrientation = orientation
                        this.profileService.save(profile)
                        return response.setStatus(HttpServletResponse.SC_ACCEPTED)
                    }
                    else {
                        println("Nie znaleziono orientacji")
                        return  response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE)
                    }
                }
                else {
                    println("Nie znaleziono plci")
                    return  response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE)
                }
            }
            else {
                println("użytkownik już istnieje")
                return  response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE)
            }
        }
        else {
            println("nie znaleziono uzytkownika")
            return  response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE)
        }
    }

    @PostMapping("login")
    fun login(@RequestBody body: LoginDTO, response: HttpServletResponse): ResponseEntity<String> {
        val res: MutableMap<String, String> = HashMap()
        res["isError"] = "NO"
        res["email"] = "OK"
        res["password"] = "OK"
        res["user"] = "OK"
        res["isActive"] = "OK"
        res["isOver18Years"] = "OK"
        val userLogin = this.userService.findByEmail(body.email)
        if(userLogin!=null){

            if (!userLogin.comparePassword(body.password)) {
                res["password"] = "ERR"
                res["isError"] = "YES"
                response.contentType = APPLICATION_JSON_VALUE
                ObjectMapper().writeValue(response.outputStream, res)
                return ResponseEntity.ok().body("Złe hasło\n")
            }

            val userId = this.userService.findIdByEmail(body.email)
            val userProfile = userService.getUser(body.email)
            if (userProfile != null) {
                val profileId = profileService.findIdByUser(body.email)
                val profile = profileService.getProfileById(profileId)
                if(profile.isActive==false || userProfile.isActive==false)
                {
                    res["isActive"] = "ERR"
                    res["isError"] = "YES"
                }
                    val localNow: LocalDate = LocalDate.now()
                    val birthDate: LocalDate = LocalDate.fromDateFields(profile.dateBirth)
                    val age: Years = Years.yearsBetween(birthDate, localNow)
                    if(age.years>=18) {
                        val accessToken:String = getAccessToken(body.email)
                        val refreshToken:String = getRefreshToken(body.email)
                        res["access_token"] = accessToken
                        res["refresh_token"] = refreshToken
                        res["profile_id"] = profileId.toString()
                        res["user_id"] = userId.toString()
                        response.contentType = APPLICATION_JSON_VALUE
                        ObjectMapper().writeValue(response.outputStream, res)
                        //val user: LoginDTO = LoginDTO(body.email, body.password, accessToken, refreshToken)
                        //println(user)
                        return ResponseEntity.ok().body("Zalogowano\n")
                    } else {
                        res["isOver18Years"] = "ERR"
                        res["isError"] = "YES"
                        response.contentType = APPLICATION_JSON_VALUE
                        ObjectMapper().writeValue(response.outputStream, res)
                        return ResponseEntity.ok().body("Konto nie ma 18 lat\n")
                    }

            }
            else {
                res["user"] = "ERR"
                res["isError"] = "YES"
                response.contentType = APPLICATION_JSON_VALUE
                ObjectMapper().writeValue(response.outputStream, res)
                return ResponseEntity.ok().body("Złe hasło\n")
            }


        } else {
            res["email"] = "ERR"
            res["isError"] = "YES"
            response.contentType = APPLICATION_JSON_VALUE
            ObjectMapper().writeValue(response.outputStream, res)
            return ResponseEntity.ok().body("Nie znaleziono usera\n")
        }




    }

    fun getAccessToken(mail: String): String {
        val algorithm = Algorithm.HMAC256("sekretnyKlucz".toByteArray())
        val token: String = JWT.create()
            .withSubject(mail)
            .withIssuedAt(Date(System.currentTimeMillis()))
            //.withExpiresAt(Date(System.currentTimeMillis()+(1000*60*600)))
            .sign(algorithm);
        return token
    }

    fun getRefreshToken(mail: String): String {
        val algorithm = Algorithm.HMAC256("sekretnyKlucz".toByteArray())
        val token: String = JWT.create()
            .withSubject(mail)
            .withIssuedAt(Date(System.currentTimeMillis()))
            .withExpiresAt(Date(System.currentTimeMillis()+(1000*60*1)))
            .sign(algorithm);

        return token
    }

}
