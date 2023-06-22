package com.example.craxinnotest.agenda.model

data class AgnedaResponseModel(
    val attendeeImgPath: String,
    val cmd: String,
    val `data`: List<Data>,
    val imgPath: String,
    val replyCode: String,
    val replyMsg: String
)