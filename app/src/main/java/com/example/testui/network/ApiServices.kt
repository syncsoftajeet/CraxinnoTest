package com.example.testui.network

import com.example.craxinnotest.agenda.model.AgnedaResponseModel
import com.example.testui.agendadetails.model.AgendDetailsResponsemodel
import com.example.testui.utils.AppCons
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiServices {


    @FormUrlEncoded
    @POST(AppCons.AGENDA_LIST)
    suspend fun getAgendaList(
        @Field("eid") eid :  String,
        @Field("pid") plid :  String
    ) : Response<AgnedaResponseModel>


    @GET(AppCons.AGENDA_DETAILS)
    suspend fun getAgendaDetails(
        @Query("aid") aid :  String
    ) : Response<AgendDetailsResponsemodel>


}