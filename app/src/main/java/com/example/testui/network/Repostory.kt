package com.example.testui.network

import androidx.lifecycle.MutableLiveData
import com.example.craxinnotest.agenda.model.AgnedaRequestModel
import com.example.craxinnotest.agenda.model.AgnedaResponseModel
import com.example.testui.agendadetails.model.AgendDetailsResponsemodel
import kotlinx.coroutines.CoroutineExceptionHandler

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class Repostory @Inject constructor(val apiServices: ApiServices) {


    suspend fun getAgendaList(agnedaRequestModel: AgnedaRequestModel): Response<AgnedaResponseModel> {
        return withContext(Dispatchers.IO) {
            apiServices.getAgendaList(agnedaRequestModel.eid, agnedaRequestModel.pid)
        }
    }

    suspend fun getAgendaDetails(aid : String): Response<AgendDetailsResponsemodel> {
        return withContext(Dispatchers.IO) {
            apiServices.getAgendaDetails(aid)
        }
    }


    fun <T> CheckingResponse(
        response: Response<T>? = null,
        responselist: MutableLiveData<NetworkResult<T>>? = null
    ) {
        try {
            if (response!!.code() == 200) {
                responselist!!.postValue(NetworkResult.Success(response.body()!!))
            } else if (response.code() == 403) {
            } else if (response.errorBody() != null) {
                try {
                    val errorobj = JSONObject(response.errorBody()!!.charStream().readText())
                    responselist!!.postValue(NetworkResult.Error(errorobj.getString("message")))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            } else
                responselist!!.postValue(NetworkResult.Error("Something Went Wrong"))

        } catch (exp: Exception) {
            exp.printStackTrace()
            responselist!!.postValue(NetworkResult.Error("Something Went Wrong"))
        }
    }

    fun <T> checkInternet(responselist : MutableLiveData<NetworkResult<T>>): CoroutineExceptionHandler{
        val coroutineExceptionHandler = CoroutineExceptionHandler{_, throwable ->
            throwable.printStackTrace()
            responselist.postValue(NetworkResult.NoInternet())
        }
        return coroutineExceptionHandler!!
    }


}