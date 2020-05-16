package com.ragingo.ragitube.models.api

import android.content.Context
import com.ragingo.ragitube.R
import com.ragingo.ragitube.models.services.youtube.YouTubeApiInterceptor
import com.ragingo.ragitube.models.services.youtube.YouTubeService
import com.ragingo.ragitube.models.services.youtube.search.SearchResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class YouTubeApiClient(
    context: Context
) {
    companion object {
        private const val BASE_URL = "https://www.googleapis.com/youtube/v3/"
    }

    private var service: YouTubeService
    private val apiKey = context.getString(R.string.google_api_key)

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val clientBuilder = OkHttpClient.Builder()
        clientBuilder.addInterceptor(
            YouTubeApiInterceptor(
                context
            )
        )
        clientBuilder.addInterceptor(interceptor)

        val moshi =
            Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()

        val retrofitBuilder =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .client(clientBuilder.build())
                .build()

        service = retrofitBuilder.create(YouTubeService::class.java)
    }

    suspend fun searchByKeyword(part: String, type: String, keyword: String, maxResults: Int): SearchResponse {
        return service.searchByKeyword(apiKey, part, type, keyword, maxResults)
    }
}
