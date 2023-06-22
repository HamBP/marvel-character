package me.algosketch.shopliveassignment.data.source.remote

import me.algosketch.shopliveassignment.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.MessageDigest
import java.time.LocalDateTime

class RetrofitFactory {
    companion object {
        private const val apiVersion = "v1"
        const val baseUrl = "https://gateway.marvel.com/$apiVersion/"

        inline fun <reified Service> create(): Service {
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(
                    OkHttpClient.Builder()
                        .addInterceptor(createLoggingInterceptor())
                        .addInterceptor(createAuthenticatingInterceptor())
                        .build())
                .build()
                .create(Service::class.java)
        }

        fun createLoggingInterceptor() : HttpLoggingInterceptor {
            return HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        }

        fun createAuthenticatingInterceptor() : Interceptor {
            return Interceptor { chain ->

                val original = chain.request()
                val httpUrl = original.url
                val ts = LocalDateTime.now().second.toString()
                val hash = md5(ts + BuildConfig.PRIVATE_KEY + BuildConfig.PUBLIC_KEY)
                val newHttpUrl = httpUrl.newBuilder()
                    .addQueryParameter("apikey", BuildConfig.PUBLIC_KEY)
                    .addQueryParameter("ts", ts)
                    .addQueryParameter("hash", hash)
                    .build()

                val newRequest = original.newBuilder()
                    .url(newHttpUrl)
                    .build()

                chain.proceed(newRequest)
            }
        }

        private fun md5(target: String): String? {
            return try {
                val md = MessageDigest.getInstance("MD5")
                md.update(target.toByteArray(charset("UTF-8")))
                val byteData = md.digest()
                val sb = StringBuffer()
                for (i in byteData.indices) sb.append(
                    ((byteData[i].toInt() and 0xff) + 0x100).toString(16).substring(1)
                )
                sb.toString()
            } catch (e: java.lang.Exception) {
                null
            }
        }
    }
}