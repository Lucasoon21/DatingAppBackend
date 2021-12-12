package com.lukasz.wolski.DatingAppBackend.controller

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm

import com.fasterxml.jackson.databind.ObjectMapper
import com.lukasz.wolski.DatingAppBackend.dtos.LoginDTO
import com.lukasz.wolski.DatingAppBackend.dtos.RegisterDTO
import com.lukasz.wolski.DatingAppBackend.dtos.RegisterDetailsDTO
import com.lukasz.wolski.DatingAppBackend.model.ProfileModel
import com.lukasz.wolski.DatingAppBackend.model.TypeGenderModel
import com.lukasz.wolski.DatingAppBackend.model.UserModel
import com.lukasz.wolski.DatingAppBackend.services.GenderService
import com.lukasz.wolski.DatingAppBackend.services.OrientationService
import com.lukasz.wolski.DatingAppBackend.services.ProfileService
import com.lukasz.wolski.DatingAppBackend.services.UserService
import org.springframework.http.*
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.*
import java.lang.Exception
import java.lang.RuntimeException
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@RestController
@RequestMapping("auth")
class AuthController(private val userService: UserService,
                     private val profileService: ProfileService,
                     private val orientationService: OrientationService,
                     private val genderService: GenderService)
{



    @PostMapping("register")
    fun register(@RequestBody body: RegisterDTO, response: HttpServletResponse) {
        val user = UserModel()
        user.email = body.email
        user.password = body.password
        user.isActive = true

        if(this.userService.emailExists(user.email)!=true){
            println("email nie istnieje")
            this.userService.save(user)
            return response.setStatus(HttpServletResponse.SC_ACCEPTED)
        }
        else{
            println("email istnieje")
            return  response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE)
        }
    }

    @PostMapping("registerDetails")
    fun registerDetails(@RequestBody body: RegisterDetailsDTO, response: HttpServletResponse) {
        println("rejestracja detale")
        val profile = ProfileModel()
        profile.data_birth = body.dateBirth
        profile.name = body.name

        val user = userService.getUser(body.email)
        val gender = genderService.getGender(body.gender)
        println("body "+body.gender)
        println("wynik "+gender)
//        val gender = genderService.getGender(body.gender)
        val orientation = orientationService.getOrientation(body.orientation)
        if(user != null){
            if(this.profileService.userExist(user)!=true){
                if(gender != null){
                    if(orientation != null) {
                        profile.user = user
                        profile.typeGender = gender
                        profile.typeOrientation = orientation
                        this.profileService.save(profile)
                        return response.setStatus(HttpServletResponse.SC_ACCEPTED)
                    }
                    else {
                        println("Nie znaleziono orientacji")
                    }
                }
                else {
                    println("Nie znaleziono plci")
                    //return response.setStatus(HttpServletResponse.SC_NOT_FOUND)
                }
            }
            else {
                println("użytkownik już istnieje")
            }
        }
        else {
            println("nie znaleziono uzytkownika")
        }
    }

    @PostMapping("login")
    fun login(@RequestBody body: LoginDTO, response: HttpServletResponse): ResponseEntity<String> {
        println("proba logowania")
        val userLogin = this.userService.findByEmail(body.email) ?: return ResponseEntity.badRequest().body("Nie znaleziono")
        if (!userLogin.comparePassword(body.password)) {
            return ResponseEntity.badRequest().body("Złe hasło")
        }
        val accessToken:String = getAccessToken(body.email)
        val refreshToken:String = getRefreshToken(body.email)
        val tokens: MutableMap<String, String> = HashMap()
        tokens["access_token"] = accessToken
        tokens["refresh_token"] = refreshToken
        response.contentType = APPLICATION_JSON_VALUE
        ObjectMapper().writeValue(response.outputStream, tokens)
        val user: LoginDTO = LoginDTO(body.email, body.password, accessToken, refreshToken)
        return ResponseEntity.ok().body("Zalogowano\n"+user)
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
            .withExpiresAt(Date(System.currentTimeMillis()+(1000*60*10)))
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
    fun tescik1():String {
        println("proba ")
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