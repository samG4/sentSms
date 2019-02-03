package learning.shadow.com.sendotp

import android.content.Context
import android.util.Log
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import learning.shadow.com.sendotp.repo.SendSMSAPI
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit


class SendMessageWorker(
        private val apiKey: String,
        private val numbers: String,
        private val message: String,
        private val sender: String,
        private val smsSentReceipt: SmsSentReceipt,
        private val context: Context) {

    private val url: String = "https://api.txtlocal.com/"

    fun sendSMS() {
        val gson = GsonBuilder()
                .setLenient()
                .create()

        Observable.fromCallable {
            val retrofit = Retrofit.Builder()
                    .baseUrl(url)
                    .client(getHotspotHttpClient(context))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build().create(SendSMSAPI::class.java)

            var service = retrofit.sendSMS(apiKey, numbers, message, sender)
            try {
                service.execute()?.let {
                    if (it.isSuccessful) {
                        Log.d("service","${it}")
                        smsSentReceipt.smsSentStatus(true)
                    } else {
                        Log.d("service","$it")
                    }
                }
            } catch (e: Exception) {
                smsSentReceipt.smsSentStatus(true)
            }
            0
        }.subscribeOn(Schedulers.io()).subscribe()
    }

    interface SmsSentReceipt {
        fun smsSentStatus(status: Boolean)
    }

    fun getHotspotHttpClient(context: Context): OkHttpClient {
        val cacheSize = 10 * 1024 * 1024 // 10 MiB
        val cache = Cache(context.cacheDir, cacheSize.toLong())
        val builder = OkHttpClient.Builder().cache(cache)
        builder.connectTimeout(5, TimeUnit.MINUTES)
        builder.readTimeout(5, TimeUnit.MINUTES)
        val loggingInterceptor = HttpLoggingInterceptor()
                .setLevel(if (BuildConfig.DEBUG)
                    HttpLoggingInterceptor.Level.BODY
                else
                    HttpLoggingInterceptor.Level.NONE)
        builder.addInterceptor(loggingInterceptor)

        return builder.build()
    }
}