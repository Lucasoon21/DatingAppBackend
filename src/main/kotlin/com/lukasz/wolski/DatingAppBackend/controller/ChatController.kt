package com.lukasz.wolski.DatingAppBackend.controller

import com.lukasz.wolski.DatingAppBackend.dtos.ConversationDTO
import com.lukasz.wolski.DatingAppBackend.dtos.ListConversationsDTO
import com.lukasz.wolski.DatingAppBackend.dtos.SendMessageDTO
import com.lukasz.wolski.DatingAppBackend.dtos.UserForMessage
import com.lukasz.wolski.DatingAppBackend.model.ChatModel
import com.lukasz.wolski.DatingAppBackend.services.ChatService
import com.lukasz.wolski.DatingAppBackend.services.ImageUserService
import com.lukasz.wolski.DatingAppBackend.services.MatchService
import com.lukasz.wolski.DatingAppBackend.services.ProfileService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.text.SimpleDateFormat
import java.util.*
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("chat")
class ChatController(private val profileService: ProfileService,
                     private val chatService: ChatService,
                     private val imageUserService: ImageUserService,
                     private val matchService: MatchService
) {

    @GetMapping("getConversation")
    fun getConversation(@RequestParam(value = "profileFirst") profileFirst: Int, @RequestParam(value = "profileSecond") profileSecond: Int ): List<ConversationDTO>? {
        if(this.profileService.profileExistById(profileFirst) && this.profileService.profileExistById(profileSecond)) {
            val profileFirst = this.profileService.getProfileById(profileFirst)
            val profileSecond = this.profileService.getProfileById(profileSecond)
            val conversations = this.chatService.getConversation(profileFirst,profileSecond)
            val conversations1 = this.chatService.getConversationn(profileFirst,profileSecond)
            val conversations2 = this.chatService.getConversationn(profileSecond,profileFirst)


            val returnConversations = ArrayList<ConversationDTO>()
            for(conversation in conversations1) {
                val profilePhoto = imageUserService.getMainPhoto(conversation.senderProfile)
                val user = UserForMessage(
                    conversation.receiverProfile.id,
                    conversation.receiverProfile.name,
                    profilePhoto?.imageLink ?: "https://ui-avatars.com/api/?background=0dbc3f&color=FFF&name="+conversation.receiverProfile.name[0]
                )
                val singleConversation = ConversationDTO(
                    conversation.id,
                    conversation.content,
                    conversation.dateMessage,
                    user
                )
                returnConversations.add(singleConversation)
            }

            for(conversation in conversations2) {
                val profilePhoto = imageUserService.getMainPhoto(conversation.senderProfile)
                val user = UserForMessage(
                    conversation.receiverProfile.id,
                    conversation.receiverProfile.name,
                    profilePhoto?.imageLink ?: "https://ui-avatars.com/api/?background=0dbc3f&color=FFF&name="+conversation.receiverProfile.name[0]
                )
                val singleConversation = ConversationDTO(
                    conversation.id,
                    conversation.content,
                    conversation.dateMessage,
                    user
                )
                returnConversations.add(singleConversation)
            }

            val xd = returnConversations.sortedWith(compareByDescending(ConversationDTO::createdAt))
            return xd
            //return returnConversations
        }
        return null
    }

    @GetMapping("getListConversation")
    fun getListConversation(@RequestParam(value = "profile") profileId: Int): List<ListConversationsDTO>? {
        if(this.profileService.profileExistById(profileId)) {
            val profile = this.profileService.getProfileById(profileId)
            val allMatch = this.matchService.getAllMatch(profile)
            if (allMatch != null) {
                val listConversations = ArrayList<ListConversationsDTO>()
                for(matches in allMatch) {
                    val firstTmp = this.chatService.getConversationBetweenTwoUsers(matches.profileFirst, matches.profileSecond)
                    val secondTmp = this.chatService.getConversationBetweenTwoUsers(matches.profileSecond, matches.profileFirst)
                    var findConversationBetweenTwoUsers: ChatModel? =  firstTmp ?: secondTmp


                    if(firstTmp!=null &&  secondTmp!=null) {

                        if(firstTmp.dateMessage>secondTmp.dateMessage) {
                            findConversationBetweenTwoUsers = firstTmp
                        } else {
                            findConversationBetweenTwoUsers = secondTmp
                        }
                    }

                    if(matches.profileFirst.id==profileId && findConversationBetweenTwoUsers!=null) {
                        val profilePhoto = imageUserService.getMainPhoto(matches.profileSecond)
                        val dateFormated:String
                        var oneDay = Date().time - (1 * 24 * 60 * 60 * 1000)
   
                        if(oneDay<findConversationBetweenTwoUsers.dateMessage.time)
                            dateFormated = SimpleDateFormat("HH:mm").format(findConversationBetweenTwoUsers.dateMessage)
                        else
                            dateFormated = SimpleDateFormat("dd/MM").format(findConversationBetweenTwoUsers.dateMessage)

                        val conversation = ListConversationsDTO(
                            matches.profileSecond.name,
                            profilePhoto?.imageLink,
                            findConversationBetweenTwoUsers.content ?: "",
                            findConversationBetweenTwoUsers.dateMessage,
                            dateFormated,
                            matches.profileSecond.id,
                        )
                        listConversations.add(conversation)
                    } else if(matches.profileSecond.id==profileId && findConversationBetweenTwoUsers!=null){
                        val profilePhoto = imageUserService.getMainPhoto(matches.profileFirst)
                        val dateFormated:String
                        val OneDay = Date().getTime() + (1 * 24 * 60 * 60 * 1000)

                        if(OneDay>findConversationBetweenTwoUsers.dateMessage.time)
                            dateFormated = SimpleDateFormat("HH:mm").format(findConversationBetweenTwoUsers.dateMessage)
                        else
                            dateFormated = SimpleDateFormat("dd/MM").format(findConversationBetweenTwoUsers.dateMessage)

                        val conversation = ListConversationsDTO(
                            matches.profileFirst.name,
                            profilePhoto?.imageLink,
                            findConversationBetweenTwoUsers.content ?: "",
                            findConversationBetweenTwoUsers.dateMessage,
                            dateFormated,
                            matches.profileFirst.id
                        )
                        listConversations.add(conversation)
                    }
                }
                val xd = listConversations.sortedWith(compareByDescending(ListConversationsDTO::dateMessage))
                return xd
            }
        } else {
            println("Nie znaleziono profilu")
        }
        return null
    }

    @PostMapping("sendMessage")
    fun sendMessage(@RequestBody body: SendMessageDTO, response: HttpServletResponse): ResponseEntity<String> {
        if(this.profileService.profileExistById(body.receiverProfileId.toInt()) && this.profileService.profileExistById(body.senderProfileId.toInt())) {
            //Sprawdzic czy jest match
            val senderProfile = this.profileService.getProfileById(body.senderProfileId.toInt())
            val receiverProfile= this.profileService.getProfileById(body.receiverProfileId.toInt())


            val match = matchService.getMatch(senderProfile, receiverProfile)
            val match2 = matchService.getMatch(receiverProfile, senderProfile)
            if(match!=null || match2!=null) {
                val newMessage = ChatModel()
                newMessage.senderProfile= senderProfile
                newMessage.receiverProfile = receiverProfile
                newMessage.content = body.contentMessage
                chatService.save(newMessage)

                return ResponseEntity.ok().body("dodano wiadomość\n")
            } else {
                println("nie znaleziono match")
                return ResponseEntity.badRequest().body("Nie znaleziono match\n")
            }
        } else {
            println("nie znaleziono użytkownika")
            return ResponseEntity.badRequest().body("Nie znaleziono użytkownika\n")

        }
    }





}