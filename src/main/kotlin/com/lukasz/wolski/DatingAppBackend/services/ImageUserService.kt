package com.lukasz.wolski.DatingAppBackend.services

import com.lukasz.wolski.DatingAppBackend.model.HobbyUserModel
import com.lukasz.wolski.DatingAppBackend.model.ImageUserModel
import com.lukasz.wolski.DatingAppBackend.model.ProfileModel
import com.lukasz.wolski.DatingAppBackend.repositories.HobbyUserRepository
import com.lukasz.wolski.DatingAppBackend.repositories.ImageUserRepository
import org.springframework.stereotype.Service

@Service
class ImageUserService(private val imageUserRepository: ImageUserRepository) {
    fun save(imageUser: ImageUserModel): ImageUserModel {
        return this.imageUserRepository.save(imageUser)
    }
    fun findAllByProfile(profile: ProfileModel): List<ImageUserModel>? {
        return this.imageUserRepository.findAllByProfile(profile)
    }
    fun deleteImage(image: Int) {
        return this.imageUserRepository.deleteById(image)
    }

    fun getImageByIdImgur(idImgur: String): ImageUserModel? {
        return this.imageUserRepository.getByIdImgur(idImgur)
    }
}