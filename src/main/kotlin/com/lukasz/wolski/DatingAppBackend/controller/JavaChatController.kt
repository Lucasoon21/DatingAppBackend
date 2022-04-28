package com.lukasz.wolski.DatingAppBackend.controller

import com.lukasz.wolski.DatingAppBackend.dtos.*
import com.lukasz.wolski.DatingAppBackend.services.ImageUserService
import com.lukasz.wolski.DatingAppBackend.services.ProfileService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.CrossOrigin
import java.util.*


@CrossOrigin
@Controller
class JavaChatController(private val profileService: ProfileService,
                         private val imageUserService: ImageUserService,
) {

    @Autowired
    private val simpMessagingTemplate: SimpMessagingTemplate? = null

    @CrossOrigin
    @MessageMapping("/message")
    @SendTo("/chatroom/public")
    fun receivePublicMessage(@Payload sendMessageDTO: SendMessageChatTestDTO): SendMessageChatTestDTO {
        println("-----------------------------------------------------------------")
        println("-----------------------------------------------------------------")

        println("jest jakas publuczna wiadomoscc===")
        return sendMessageDTO
    }

    @CrossOrigin
    @MessageMapping("/private-message")
    //@SendTo("/chatroom/public")
    fun receivePrivateMessage(@Payload sendMessageDTO: SendMessageChatTestDTO): SendMessageChatTestDTO {
        println("-----------------------------------------------------------------")
        println("-----------------------------------------------------------------")

        println("JEst wiadomosc prywatna")
        simpMessagingTemplate!!.convertAndSendToUser(
            sendMessageDTO.receiverProfileId.toString(),
            "/private",
            sendMessageDTO
        )
        println(sendMessageDTO.toString())
        return sendMessageDTO
    }

    @MessageMapping("/chat/{to}")
    fun sendMessage(@DestinationVariable to: String, message: SendMessageDTO) {
//    fun sendMessage(@DestinationVariable to: String, message: SendMessageChatTestDTO) {
        println("handling send message: ${message.contentMessage} to: $to from: ${message.senderProfileId}")

        if(this.profileService.profileExistById(to.toInt()) && this.profileService.profileExistById(message.senderProfileId.toInt())) {
            val profileSender = this.profileService.getProfileById(message.senderProfileId.toInt())
            val profileReceiver = this.profileService.getProfileById(to.toInt())
            val profilePhoto = imageUserService.getMainPhoto(profileSender)
            val user = UserForMessage(
                profileReceiver.id,
                profileReceiver.name,
                profilePhoto?.imageLink ?: "https://ui-avatars.com/api/?background=0dbc3f&color=FFF&name="+profileReceiver.name[0]
            )
            val singleConversation = ConversationSocketDTO(
                UUID.randomUUID().toString(),
                message.contentMessage,
                Date(),
                message.senderProfileId.toInt(),
                user
            )
            println("Wyslano wiadomosc")
            simpMessagingTemplate!!.convertAndSend("/topic/messages/$to", singleConversation)
        } else {
            println("uzytkownicy nie istnieja")
        }

    }

}