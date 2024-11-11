package com.ddm.steps.firebase.readtogether.ui.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.POST
import retrofit2.http.Query

interface NotificationApi {
    @POST("/api/notifications/friend-request")
    suspend fun sendFriendRequestNotification(
        @Query("fcmToken") fcmToken: String,
        @Query("senderName") senderName: String
    )
}

object RetrofitInstance {
    private const val BASE_URL = "http://192.168.0.108:8080"

    val api: NotificationApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NotificationApi::class.java)
    }
}