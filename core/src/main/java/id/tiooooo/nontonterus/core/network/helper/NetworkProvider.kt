package id.tiooooo.nontonterus.core.network.helper

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NetworkProvider(
    private val context: Context,
    private val ioDispatcher: CoroutineDispatcher,
    private val url: String,
) {

    fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(url)
            .client(createOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
    }

    private fun createOkHttpClient(): OkHttpClient {
        val httpBuilder = OkHttpClient.Builder()
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .retryOnConnectionFailure(true)
            .addInterceptor(
                HeaderInterceptor(
                    ioDispatcher = ioDispatcher,
                )
            )
            .addInterceptor(getChuckerInterceptor())

        return httpBuilder.build()
    }


    private fun getChuckerInterceptor(): ChuckerInterceptor {
        return ChuckerInterceptor.Builder(context).build()
    }

}
