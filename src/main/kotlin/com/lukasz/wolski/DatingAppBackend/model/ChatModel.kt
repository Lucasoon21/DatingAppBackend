package com.lukasz.wolski.DatingAppBackend.model

import lombok.NoArgsConstructor
import java.util.*
import javax.persistence.*

@NoArgsConstructor
@Entity(name="chat")
data class ChatModel(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0,

    @ManyToOne
    @JoinColumn(name="profile_sender")
    var senderProfile: ProfileModel = ProfileModel(),

    @ManyToOne
    @JoinColumn(name="profile_reciever")
    var receiverProfile: ProfileModel = ProfileModel(),

    @Column
    var content: String = "",

    @Column
    var dateMessage: Date = Date(),
)
