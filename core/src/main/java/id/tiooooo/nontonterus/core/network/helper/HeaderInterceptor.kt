package id.tiooooo.nontonterus.core.network.helper

import id.tiooooo.nontonterus.core.constant.Constant
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.Interceptor
import okhttp3.Response
import java.text.SimpleDateFormat
import java.util.Locale

class HeaderInterceptor(
    private val ioDispatcher: CoroutineDispatcher,
) : Interceptor {

    companion object {
        private const val HEADER_TIME_ZONE = "local_tz"
        private const val HEADER_AUTHORIZATION = "Authorization"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val currentTimezone =
            SimpleDateFormat("Z", Locale.getDefault()).format(System.currentTimeMillis())
        val timeZone = currentTimezone.substring(1, 3).toIntOrNull() ?: 7

        val requestBuilder = request.newBuilder()
            .header(HEADER_TIME_ZONE, timeZone.toString())

        requestBuilder.header(
            HEADER_AUTHORIZATION,
            "Bearer ${Constant.API_KEY}"
        )
        requestBuilder.header("accept", "application/json")

        val requestBuild = requestBuilder.build()
        return chain.proceed(requestBuild)
    }
}
