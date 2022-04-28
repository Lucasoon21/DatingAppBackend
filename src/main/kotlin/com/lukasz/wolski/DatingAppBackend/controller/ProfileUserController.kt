package com.lukasz.wolski.DatingAppBackend.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.lukasz.wolski.DatingAppBackend.dtos.*
import com.lukasz.wolski.DatingAppBackend.model.HobbyUserModel
import com.lukasz.wolski.DatingAppBackend.model.ImageUserModel
import com.lukasz.wolski.DatingAppBackend.model.InterestedRelationshipModel
import com.lukasz.wolski.DatingAppBackend.services.*
import org.joda.time.LocalDate
import org.joda.time.Years
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import java.util.regex.Pattern
import javax.servlet.http.HttpServletResponse
import kotlin.collections.ArrayList

@RestController
@RequestMapping("profile")
class ProfileUserController(
    private val userService: UserService,
    private val profileService: ProfileService,
    private val genderService: GenderService,
    private val dictionaryService: DictionaryService,
    private val orientationService: OrientationService,
    private val hobbyUserService: HobbyUserService,
    private val interestedRelationshipService: InterestedRelationshipService,
    private val imageService: ImageUserService
) {

    val PASSWORD_PATTERH: Pattern = Pattern.compile("^((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[\\W]).{6,20})$")

    fun isValidPassword(str: String): Boolean{
        return PASSWORD_PATTERH.matcher(str).matches()
    }

    @PutMapping("changePassword")
    fun changePassword(@RequestBody body: ChangePasswordDTO, response: HttpServletResponse): ResponseEntity<String> {
        if (this.profileService.profileExistById(body.profileId)) {
            val profile = this.profileService.getProfileById(body.profileId)
            val user = userService.getById(profile.user.id)
            if(user!=null) {
                if (!user.comparePassword(body.oldPassword)) {
                    println("Złe hasło")
                    return ResponseEntity.badRequest().body("Złe hasło")

                }
                if(isValidPassword(body.newConfirmPassword) && isValidPassword(body.newPassword) &&
                        body.newPassword==body.newPassword) {
                    println("ZMiana hasła")
                    user.password = body.newPassword
                    userService.save(user)
                    return ResponseEntity.ok().body("Zmieniono hasło")

                } else {
                    println("Nie spełnia wymagań")
                    return ResponseEntity.badRequest().body("Hasła są różne lub nie spełnia wymagań")
                }

            } else {
                println("Nie znaleziono użytkownika")
            }
        }
        println("Błąd")
        return ResponseEntity.badRequest().body("Bład")
    }


    @PutMapping("deactivateAccount")
    fun deactivateAccount(@RequestParam(value = "profile") profileId: Int, response: HttpServletResponse): ResponseEntity<String> {
        if (this.profileService.profileExistById(profileId)) {
            val profile = this.profileService.getProfileById(profileId)
            profile.isActive=false
            profileService.save(profile)



            val user = userService.getById(profile.user.id)
            if (user != null) {
                user.isActive=false
                userService.save(user)
                return ResponseEntity.ok().body("Deaktywowano konto")
            } else {
                return ResponseEntity.badRequest().body("Bład")
            }
        }
        println("Błąd")
        return ResponseEntity.badRequest().body("Bład")
    }

    @DeleteMapping("deleteAccount")
    fun deleteAccount(@RequestParam(value = "profile") profileId: Int, response: HttpServletResponse): ResponseEntity<String>  {
        if (this.profileService.profileExistById(profileId)) {
            val profile = profileService.getProfileById(profileId)
            profileService.deleteAccount(profile.id)
            val user = userService.getById(profile.user.id)
            if (user != null) {
                userService.deleteAccount(user.id)
            }

                return ResponseEntity.ok().body("usunięto konot\n")

        } else {
            println("nie znaleziono konta")
        }
        return ResponseEntity.badRequest().body("Nie usunięto konta\n")
    }


    @PutMapping("activateAccount")
    fun activateAccount(@RequestParam(value = "profile") profileId: Int, response: HttpServletResponse): ResponseEntity<String> {
        if (this.profileService.profileExistById(profileId)) {
            val profile = this.profileService.getProfileById(profileId)
            profile.isActive=true
            profileService.save(profile)
            val user = userService.getById(profile.user.id)
            if (user != null) {
                user.isActive=true
                userService.save(user)
                return ResponseEntity.ok().body("aktywowano konto")
            } else {
                return ResponseEntity.badRequest().body("Bład")
            }
        }
        println("Błąd")
        return ResponseEntity.badRequest().body("Bład")
    }







    @PutMapping("changeDescription")
    fun changeDescription(@RequestBody body: ChangeDescriptionDTO, response: HttpServletResponse): ResponseEntity<String> {

        if (this.profileService.profileExistById(body.profileId)) {
            val oldProfile = this.profileService.getProfileById(body.profileId)
            oldProfile.description = body.description
            this.profileService.save(oldProfile)

            return ResponseEntity.ok().body("Zmieniono opis\n")
        } else {
            println("taki profile nie istnieje")
            return ResponseEntity.badRequest().body("Nie zminiono opisu\n")
        }
    }


    @PutMapping("changeProfileDetails")
    fun changeProfileDetails(@RequestBody body: ChangeProfileDetailsDTO, response: HttpServletResponse): ResponseEntity<String> {

        if (this.profileService.profileExistById(body.profileId)) {
            println(body.profileId)
            val oldProfile = profileService.getProfileById(body.profileId)
            val orientation = orientationService.getOrientation(body.orientationId)
            val height = body.height
            val weight = body.weight
            val alcohol = dictionaryService.getAlcohol(body.alcoholId)
            val children = dictionaryService.getChildren(body.childrenId)
            val cigarettes = dictionaryService.getCigarettes(body.cigarettesId)
            val education = dictionaryService.getEducation(body.educationId)
            val eyeColor = dictionaryService.getEyeColor(body.eyeColorId)
            val religious = dictionaryService.getReligious(body.religiousId)
            val job = body.job
            //val zodiac = dictionaryService.getZodiac(body.zodiacId)

            if(orientation != null && (height in 140..200) && (weight in 40..130) && alcohol!=null
                && children!=null && cigarettes!=null && education!=null && eyeColor!=null
                    && religious!=null) {
                oldProfile.height = height
                oldProfile.weight = weight
                oldProfile.dictionaryOrientation = orientation
                oldProfile.dictionaryAlcohol = alcohol
                oldProfile.dictionaryChildren = children
                oldProfile.dictionaryCigarettes = cigarettes
                oldProfile.dictionaryEducation = education
                oldProfile.dictionaryEyeColor = eyeColor
                oldProfile.dictionaryReligious = religious
                oldProfile.job = job
                this.profileService.save(oldProfile)
                return ResponseEntity.ok().body("Zmieniono szczegóły profilu\n")


            } else {
                println("Więcej niż jedno pole jest puste")
                return ResponseEntity.badRequest().body("Jedno pole lub więcej jest puste\n")
            }
        } else {
            println("taki profile nie istnieje")
            return ResponseEntity.badRequest().body("Nie znaleziono użytkownika\n")
        }
    }

    @RequestMapping("getProfileRelationship")
    fun getProfileRelationship(@RequestParam(value = "profile") profileId: Int): ArrayList<InterestedRelationshipDTO>? {
        if (this.profileService.profileExistById(profileId)) {
            val listRelationship = dictionaryService.getAllRelationshipDictionary()
            val profile = this.profileService.getProfileById(profileId)
            val relationshipUser  = this.interestedRelationshipService.getAllInterestedRelationshipByProfile(profile)
            // val returnListHobby: ArrayList<HobbyUserDTO> = emptyList<HobbyUserDTO>() as ArrayList<HobbyUserDTO>
            val returnListRelationship = ArrayList<InterestedRelationshipDTO>()
            if(listRelationship!=null){
                for(item in listRelationship){
                    val relationship: InterestedRelationshipDTO = InterestedRelationshipDTO(
                        profileId,
                        item.id,
                        0,
                        item.name
                    )
                    returnListRelationship.add(relationship)
                }
                for(item in relationshipUser){
                    val e = returnListRelationship.indexOfFirst { it.relationshipId == item.relationship.id }
                    returnListRelationship[e].decision = item.decison
                }
                return returnListRelationship
            }
            else {
                println("Użytkownik nie ma wybranego hobby")
            }
        } else {
            println("Nie znaleziono")
        }
        return null;
    }

    @PutMapping("changeProfileRelationship")
    fun changeProfileRelationship(@RequestBody body: ChangeProfileRelationshipDTO, response: HttpServletResponse) {
        val profileId = body.profileId
        val newRelationshipList = body.listRelationship

        if (this.profileService.profileExistById(profileId)) {
            val profile = this.profileService.getProfileById(profileId)
            val listRelationshipUser =  this.interestedRelationshipService.getAllInterestedRelationshipByProfile(profile)
            val listRelationship = dictionaryService.getAllRelationshipDictionary()
            if (listRelationship != null) {
                for(newRelationship in newRelationshipList) {
                    val relationship = this.dictionaryService.getRelationship(newRelationship.relationshipId)
                    if(relationship != null){
                        val oldRelationship = this.interestedRelationshipService.getRelationshipUserByProfileAndRelationshipDictionary(profile, relationship)
                        if(oldRelationship!=null){
                            oldRelationship.decison = newRelationship.decision
                            this.interestedRelationshipService.save(oldRelationship)
                        } else {
                            val newRelationshipModel = InterestedRelationshipModel()
                            newRelationshipModel.relationship =relationship
                            newRelationshipModel.decison = newRelationship.decision
                            newRelationshipModel.profile = profile
                            this.interestedRelationshipService.save(newRelationshipModel)
                        }
                        //println(oldRelationship.id)
                    } else {
                        println("nie znaleziono relacji")
                    }
                }
            } else{
                println("lista relacji jest pusta")
            }
        } else {
            println("nie znaleziono uzytkownika")
        }
        println("ddd")
    }

    @PutMapping("getProfileDetails")
    fun getProfileDetails(@RequestBody body: GetProfileDTO, response: HttpServletResponse): ResponseEntity<ProfileDTO> {

        if (this.profileService.profileExistById(body.profileId)) {
            val oldProfile = this.profileService.getProfileById(body.profileId)

            //val ageUser: Int = oldProfile.data_birth.
            val localNow: LocalDate = LocalDate.now()
            val birthDate: LocalDate = LocalDate.fromDateFields(oldProfile.date_birth)
            val age: Years = Years.yearsBetween(birthDate, localNow)


            val responseProfile:ProfileDTO = ProfileDTO(
                oldProfile.name ?: "",
                oldProfile.dictionaryGender?.name ?: "",
                oldProfile.dictionaryOrientation?.name?: "",
                oldProfile.description?: "",
                oldProfile.location?: "",
                oldProfile.dictionaryAlcohol?.name?: "",
                oldProfile.job?: "",
                oldProfile.height?: 0,
                oldProfile.weight?: 0,
                oldProfile.dictionaryEducation?.name ?: "",
                oldProfile.dictionaryReligious?.name?: "",
                oldProfile.dictionaryChildren?.name ?: "",
                oldProfile.dictionaryCigarettes?.name?: "",
                oldProfile.dictionaryEyeColor?.name?: "",
                oldProfile.dictionaryZodiac?.name ?: "",
                age.years?:0,
            )


            ObjectMapper().writeValue(response.outputStream, responseProfile)
            return ResponseEntity.ok().body(responseProfile)

        } else {
            println("taki profile nie istnieje")
            return ResponseEntity.badRequest().body(null)
        }
    }

    @RequestMapping("getProfileHobby")
    fun getProfileHobby(@RequestParam(value = "profile") profileId: Int): ArrayList<HobbyUserDTO>? {
        if (this.profileService.profileExistById(profileId)) {
            val listHobby = dictionaryService.getAllHobbyDictionary()
            val profile = this.profileService.getProfileById(profileId)
            val hobbyUser  = this.hobbyUserService.getAllHobbyByProfile(profile)

            // val returnListHobby: ArrayList<HobbyUserDTO> = emptyList<HobbyUserDTO>() as ArrayList<HobbyUserDTO>
            val returnListHobby = ArrayList<HobbyUserDTO>()
            if(listHobby!=null){
                for(item in listHobby){
                    val hobby: HobbyUserDTO = HobbyUserDTO(
                        profileId,
                        item.id,
                        0,
                        item.name
                    )
                    returnListHobby.add(hobby)
                }
                for(item in hobbyUser){
                    val e = returnListHobby.indexOfFirst { it.hobbyId == item.hobby.id }
                    returnListHobby[e].decision = item.decison
                }
                return returnListHobby
            }
            else {
                println("Użytkownik nie ma wybranego hobby")
            }


        } else {
            println("Nie znaleziono")
        }
        return null;
    }

    @PutMapping("changeProfileHobby")
    fun changeProfileHobby(@RequestBody body: ChangeProfileHobbyDTO, response: HttpServletResponse) {
        val profileId = body.profileId
        val newHobbyList = body.listHobby
        if (this.profileService.profileExistById(profileId)) {
            val profile = this.profileService.getProfileById(profileId)
            val listHobbyUser =  this.hobbyUserService.getAllHobbyByProfile(profile)
            val listHobby = dictionaryService.getAllHobbyDictionary()
            if (listHobby != null) {
                for(newHobby in newHobbyList) {
                    val hobby = this.dictionaryService.getHobby(newHobby.hobbyId)
                    if(hobby != null){
                        val oldHobby = this.hobbyUserService.getHobbyUserByHobbyIdAndProfileId(profile,hobby)
                        if(oldHobby != null) {
                            oldHobby.decison = newHobby.decision
                            this.hobbyUserService.save(oldHobby)
                        } else {
                            val newHobbyModel = HobbyUserModel()
                            newHobbyModel.hobby =hobby
                            newHobbyModel.decison = newHobby.decision
                            newHobbyModel.profile = profile
                            this.hobbyUserService.save(newHobbyModel)
                        }
                    } else {
                        println("nie znaleziono hobby")
                    }
                    /* } else {
                         val hobby = this.dictionaryService.getHobby(newHobby.hobbyId)

                         if (hobby != null) {
                             val newHobbyModel = HobbyUserModel()
                             newHobbyModel.hobby =hobby
                             newHobbyModel.decison = newHobby.decision
                             newHobbyModel.profile = profile
                             this.hobbyUserService.save(newHobbyModel)
                         }else {
                             println("nie znaleziono hobby")
                         }
                     }*/
                }
            } else{
                println("lista hobby jest pusta")
            }
        } else {
            println("nie znaleziono uzytkownika")
        }
        println(body.listHobby)
    }

    @PutMapping("uploadImage")
    fun uploadImage(@RequestBody body: ProfileImageDTO, response: HttpServletResponse): ResponseEntity<String> {

        if (this.profileService.profileExistById(body.profileId)) {
            val profile = this.profileService.getProfileById(body.profileId)
            val imagesProfile = this.imageService.getAllMainPhoto(profile)
            println(imagesProfile)
            val image = ImageUserModel()
            image.createDate = Date()
            image.deleteHashImgur=body.deleteHashImgur
            image.idImgur=body.idImgur
            image.imageLink=body.linkImgur
            image.profile=profile
            image.mainPicture = imagesProfile?.isEmpty() ?: true

            this.imageService.save(image)

            //return ResponseEntity.badRequest().body("Dodano zdjęcie\n")
            return ResponseEntity.ok().body("Dodano zdjęcie\n")
        } else {
            println("taki profile nie istnieje")
            return ResponseEntity.badRequest().body("Nie dodano zdjęcia\n")
        }
    }

    @RequestMapping("getProfileImages")
    fun getProfileImages(@RequestParam(value = "profile") profileId: Int): ArrayList<ProfileImageDTO>? {
        if (this.profileService.profileExistById(profileId)) {
            val profile = profileService.getProfileById(profileId)
            val listProfileImages = imageService.findAllByProfile(profile)
            if(listProfileImages!=null){
                val returnListImages = ArrayList<ProfileImageDTO>()

                for(item in listProfileImages){
                    val image: ProfileImageDTO = ProfileImageDTO(
                        profileId,
                        item.deleteHashImgur,
                        item.idImgur,
                        item.imageLink,
                        item.mainPicture
                    )
                    returnListImages.add(image)
                }
                return returnListImages
            }
        }
        return null
    }

    @RequestMapping("getMainPhotoProfile")
    fun getMainPhotoProfile(@RequestParam(value = "profile") profileId: Int): ImageUserModel? {
        if (this.profileService.profileExistById(profileId)) {
            val profile = profileService.getProfileById(profileId)
            val elo = imageService.getMainPhoto(profile)
            return elo
        }
        return null
    }

    @PutMapping("changeMainPhotoProfile")
    fun changeMainPhotoProfile(@RequestBody body: ChangeMainPhotoDTO, response: HttpServletResponse): ResponseEntity<String> {
        if (this.profileService.profileExistById(body.profileId)) {
            val profile = profileService.getProfileById(body.profileId)
            val allMainPhotos = imageService.findAllByProfile(profile)
            if (allMainPhotos!=null) {
                for(photos in allMainPhotos) {
                    photos.mainPicture=false
                    imageService.save(photos)
                }
            }
            val newMainPhoto = imageService.getImageByIdImgur(body.imgurPhotoId)
            if(newMainPhoto!=null) {
                newMainPhoto.mainPicture=true
                imageService.save(newMainPhoto)
                return ResponseEntity.ok().body("Zmieniono zdjęcie profilowe zdjęcie\n")
            } else {
                return ResponseEntity.badRequest().body("Nie znaleziono zdjęcia")
            }


        }
        return ResponseEntity.badRequest().body("Pojawił się błąd zdjęcia")
    }



    @DeleteMapping("deleteProfileImage")
    fun deleteImageProfile(@RequestBody body: ProfileImageDTO): ResponseEntity<String> {
        if (this.profileService.profileExistById(body.profileId)) {
            val image = imageService.getImageByIdImgur(body.idImgur)
            val profile = profileService.getProfileById(body.profileId)
            if (image != null) {
                if(image.mainPicture) {
                    val newPhotoProfile = imageService.setNewPhotoProfileAfterDelete(profile)
                    if (newPhotoProfile != null) {
                        newPhotoProfile.mainPicture=true
                        imageService.save(newPhotoProfile);
                    }
                }
                imageService.deleteImage(image.id)

                return ResponseEntity.ok().body("usunięto zdjęcia\n")
            } else {
                println("nie usunieto")
            }
        } else {
            println("nie znaleziono profilu")
        }
        return ResponseEntity.badRequest().body("Nie usunięto zdjęcia\n")
    }
}