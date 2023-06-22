package com.example.testui.agenda.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.craxinnotest.agenda.model.AgnedaRequestModel
import com.example.craxinnotest.agenda.model.AgnedaResponseModel
import com.example.testui.agendadetails.model.AgendDetailsResponsemodel
import com.example.testui.network.NetworkResult
import com.example.testui.network.Repostory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import javax.inject.Inject


@HiltViewModel
class AgendaViewModel @Inject constructor(val repostory: Repostory) :ViewModel() {


    val agendaresponse = MutableLiveData<NetworkResult<AgnedaResponseModel>>()
    val agendaDetailslResponse = MutableLiveData<NetworkResult<AgendDetailsResponsemodel>>()


    suspend fun getAgendaList(agnedaRequestModel: AgnedaRequestModel){
        viewModelScope.launch(repostory.checkInternet(agendaresponse)) {
            agendaresponse.postValue(NetworkResult.Loading())
            val response = repostory.getAgendaList(agnedaRequestModel)
            repostory.CheckingResponse(response,agendaresponse)
        }
    }

    suspend fun getAgendaDetails(aid : String){
        viewModelScope.launch(repostory.checkInternet(agendaDetailslResponse)) {
            agendaDetailslResponse.postValue(NetworkResult.Loading())
            val response = repostory.getAgendaDetails(aid)
            repostory.CheckingResponse(response,agendaDetailslResponse)
        }
    }
}
