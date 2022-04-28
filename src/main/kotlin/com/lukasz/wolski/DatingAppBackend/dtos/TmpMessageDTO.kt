package com.lukasz.wolski.DatingAppBackend.dtos

import lombok.NoArgsConstructor
import lombok.ToString

@NoArgsConstructor
@ToString
data class TmpMessageDTO (
    val senderName:String,
    val receiverName: String,
    val message: String,
    val date: String,
    val status: Status,
)

