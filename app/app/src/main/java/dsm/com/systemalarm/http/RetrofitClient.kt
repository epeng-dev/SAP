package dsm.com.systemalarm.http


import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by mskan on 2018-04-12.
 */
object RetrofitClient {
    private var outInstance : Retrofit? = null

    val logInterceptor : HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }

    val client : OkHttpClient = OkHttpClient.Builder().apply {
        this.addInterceptor(logInterceptor)
    }.connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS).build()


    val instance: Retrofit?
        get() {
            if (outInstance == null){
                outInstance = Retrofit.Builder()
                        .baseUrl("http://10.0.2.2:8080/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .client(client)
                        .build()
            }
            return outInstance
        }
}