package com.example.testui.agendadetails.model

import com.example.craxinnotest.agenda.model.Attendee

data class Data(
    val agenda_documents: List<AgendaDocument>,
    val agenda_speakers: List<AgendaSpeaker>,
    val attendees: List<Attendee>,
    val description: String,
    val end_date: String,
    val event_id: Int,
    val header_img: String,
    val id: Int,
    val location_name: String,
    val my_agenda: Int,
    val name: String,
    val register_links: List<RegisterLink>,
    val sponsor_img: String,
    val sponsor_name: String,
    val start_date: String,
    val user_id: Int
)