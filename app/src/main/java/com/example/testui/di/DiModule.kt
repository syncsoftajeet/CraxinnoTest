package com.example.testui.di

import com.example.testui.network.ApiServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DiModule {





    @Singleton
    @Provides
    fun getBaseUrl() : String = "http://eventowl.net:3680/"


    @Singleton
    @Provides
    fun RetrofitInstance(baseurl : String) : Retrofit{
        return Retrofit.Builder().baseUrl(baseurl).addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Singleton
    @Provides
    fun ApiInstance(retrofit: Retrofit) : ApiServices{
        return retrofit.create(ApiServices::class.java)
    }


}