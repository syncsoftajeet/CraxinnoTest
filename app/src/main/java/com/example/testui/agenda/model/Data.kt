package com.example.craxinnotest.agenda.model

data class Data(
    val attendees: List<Attendee>,
    val end_date: String,
    val id: Int,
    val my_agenda: Int,
    val name: String,
    val start_date: String
)