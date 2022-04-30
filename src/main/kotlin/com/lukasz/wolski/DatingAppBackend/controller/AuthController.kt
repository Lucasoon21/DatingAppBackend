package com.lukasz.wolski.DatingAppBackend.controller

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.fasterxml.jackson.databind.ObjectMapper
import com.lukasz.wolski.DatingAppBackend.dtos.LoginDTO
import com.lukasz.wolski.DatingAppBackend.dtos.LoginStatusDTO
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

    val EMAIL_ADDRESS_PATTERN: Pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    val PASSWORD_PATTERH: Pattern = Pattern.compile("^((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[\\W]).{6,20})$")
    fun isValidEmail(str: String): Boolean{

        return EMAIL_ADDRESS_PATTERN.matcher(str).matches()
    }
    fun isValidPassword(str: String): Boolean{
        return PASSWORD_PATTERH.matcher(str).matches()
    }
    @PostMapping("register")
    fun register(@RequestBody body: RegisterDTO, response: HttpServletResponse) {
        val email = body.email.lowercase()
        val res: MutableMap<String, String> = HashMap()
        res["isError"] = "NO"
        res["emailExists"] = "NO"
        res["errorValidate"] = "NO"
        if(isValidEmail(email) && isValidPassword(body.password) && isValidPassword(body.confirmPassword) && body.confirmPassword == body.password){
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
            profile.date_birth = body.dateBirth
            profile.dictionaryGender = gender
            profile.dictionaryOrientation = orientation

            this.profileService.save(profile)
        } else {
            println("nie istnieje mail")
        }
    }

    @PostMapping("registerDetails")
    fun registerDetails(@RequestBody body: RegisterDetailsDTO, response: HttpServletResponse) {

        println(body.orientation)
        val profile = ProfileModel()
        profile.date_birth = body.dateBirth
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
/*
*                 val localNow: LocalDate = LocalDate.now()
                val birthDate: LocalDate = LocalDate.fromDateFields(profile.date_birth)
                val age: Years = Years.yearsBetween(birthDate, localNow)
                if(age.years>=18) {

                }
* */
    @PostMapping("login")
    fun login(@RequestBody body: LoginDTO, response: HttpServletResponse): ResponseEntity<String> {
        println("proba logowania")
        println(body)
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
                println(userProfile.id)
                val profileId = profileService.findIdByUser(body.email)
                val profile = profileService.getProfileById(profileId)
                if(profile.isActive==false || userProfile.isActive==false) {
                    res["isActive"] = "ERR"
                    res["isError"] = "YES"
                }
                    val localNow: LocalDate = LocalDate.now()
                    val birthDate: LocalDate = LocalDate.fromDateFields(profile.date_birth)
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
                        val user: LoginDTO = LoginDTO(body.email, body.password, accessToken, refreshToken)
                        println(user)
                        return ResponseEntity.ok().body("Zalogowano\n"+user)
                    } else {
                        res["isOver18Years"] = "ERR"
                        res["isError"] = "YES"
                        response.contentType = APPLICATION_JSON_VALUE
                        ObjectMapper().writeValue(response.outputStream, res)
                        return ResponseEntity.ok().body("Konto nie ma 18 lat\n")
                    }
                /*} else {
                    res["isActive"] = "ERR"
                    res["isError"] = "YES"
                    response.contentType = APPLICATION_JSON_VALUE
                    ObjectMapper().writeValue(response.outputStream, res)
                    return ResponseEntity.ok().body("Konto nie jest aktywne\n")
                }*/
            } else {
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


    @RequestMapping("hello")
    fun helloWorld(@RequestParam(value = "name", defaultValue = "World") name: String): String? {
        return "Hello $name!!"
    }
/*Jak wspomniano, ze względów bezpieczeństwa tokeny dostępu mogą być ważne przez krótki czas.
Po wygaśnięciu aplikacje klienckie mogą użyć tokenu odświeżania do „odświeżenia” tokenu dostępu.
Oznacza to, że token odświeżania to artefakt poświadczeń, który umożliwia aplikacji klienckiej
 uzyskanie nowych tokenów dostępu bez konieczności ponownego logowania się użytkownika.*/
    @GetMapping("/token/refresh")
    fun refreshToken(request: HttpServletRequest, response: HttpServletResponse): String
    {
        val authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION)
        println(request.getHeader(HttpHeaders.AUTHORIZATION))
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                val refreshToken = authorizationHeader.substring("Bearer ".length)
                val algorithm = Algorithm.HMAC256("sekretnyKlucz".toByteArray())
                val verifier = JWT.require(algorithm).build()
                val decodedJWT = verifier.verify(refreshToken)
                val username = decodedJWT.subject
                val user: UserModel = userService.getUser(username)!!
                val accessToken = getAccessToken(user.email)
                val tokens: MutableMap<String, String> = HashMap()
                tokens["access_token"] = accessToken
                tokens["refresh_token"] = refreshToken
                response.contentType = APPLICATION_JSON_VALUE
                ObjectMapper().writeValue(response.outputStream, tokens)
            } catch (err: Exception) {
                response.setHeader("error", err.message)
                response.status = HttpStatus.FORBIDDEN.value()
                // response.sendError(FORBIDDEN.value());
                val error: MutableMap<String, String?> = HashMap()
                error["error message"] = err.message
                response.contentType = MediaType.APPLICATION_JSON_VALUE
                ObjectMapper().writeValue(response.outputStream, error)
            }
        } else {
            throw RuntimeException("Refersh Token is missing")
        }
        return "ok"
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

    @GetMapping("testApi")
    fun tescik1(response: HttpServletResponse):String {
        println("proba ")
        println(response)
        return "hello1"
    }
    @GetMapping("test/tescik2")
    fun tescik2():String {
        return "hello2"
    }
    @GetMapping("test/tescik3")
    fun tescik3():String {
        return "hello3"
    }
}
/*

    fun getRefreshToken(usermail: String) : String
    {
        val secretKey: String = "sekretnyKlucz"
        val grantedAuthority = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ACTIVE")
        val curTimeMillis = System.currentTimeMillis()
        val tokenJwt: String? = Jwts
            .builder()
            .setId("DatingAppJWT")
            .setSubject(usermail)
            /*  .claim("authorities",
                  grantedAuthority.stream()
                      .map(GrantedAuthority::getAuthority)
                      .collect(Collectors.toList())
              )*/
            .setIssuedAt(Date(curTimeMillis))
            .setExpiration(Date(curTimeMillis+(1000*60*30)))
            .signWith(SignatureAlgorithm.HS512,secretKey.toByteArray())
            .compact()

        return tokenJwt.toString()
    }

*/