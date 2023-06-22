package com.example.testui.agendadetails.model

data class AgendDetailsResponsemodel(
    val attendeeImgPath: String,
    val cmd: String,
    val `data`: Data,
    val imgPath: String,
    val replyCode: String,
    val replyMsg: String,
    val speakerImgPath: String
)