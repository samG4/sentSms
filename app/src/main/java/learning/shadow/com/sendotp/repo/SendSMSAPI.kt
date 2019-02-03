package learning.shadow.com.sendotp.repo

import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query

interface SendSMSAPI {

    @POST("send/")
    fun sendSMS(
            @Query("apikey") apiKey: String,
            @Query("numbers") numbers: String,
            @Query("message")message: String,
            @Query("sender")sender: String): Call<String>

}