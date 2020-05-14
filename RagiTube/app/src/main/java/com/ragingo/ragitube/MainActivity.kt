package com.ragingo.ragitube

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.youtube.player.YouTubeStandalonePlayer
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.security.MessageDigest


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val apiKey = getString(R.string.google_api_key)

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val clientBuilder = OkHttpClient.Builder()
        clientBuilder.addInterceptor(YouTubeApiInterceptor(this))
        clientBuilder.addInterceptor(interceptor)

        val moshi =
            Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()

        val retrofitBuilder =
            Retrofit.Builder()
                .baseUrl("https://www.googleapis.com/youtube/")
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .client(clientBuilder.build())
                .build()
        val retrofit = retrofitBuilder.create(Search::class.java)

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val res = retrofit.getList(apiKey, "snippet", "video", "あつ森")
                println(res.kind)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

//        val videoId = "mw5VIEIvuMI"
//        val intent = YouTubeStandalonePlayer.createVideoIntent(this, apiKey, videoId);
//        startActivity(intent);
    }
}

class YouTubeApiInterceptor(
    private val context: Context
) : Interceptor {
    private companion object {
        fun isYouTube(url: HttpUrl): Boolean {
            if (url.host() != "www.googleapis.com") {
                return false
            }
            val segments = url.pathSegments()
            if (segments.size < 3) {
                return false
            }
            return segments[0] == "youtube" && segments[1] == "v3"
        }
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val req = chain.request()
        if (!isYouTube(req.url())) {
            return chain.proceed(req)
        }
        val newReq =
            req.newBuilder()
                .addHeader("X-Android-Package", BuildConfig.APPLICATION_ID)
                .addHeader("X-Android-Cert", getSha1())
                .build()
        return chain.proceed(newReq)
    }

    private fun getSha1(): String {
        val signatures =
            context.packageManager
                .getPackageInfo(BuildConfig.APPLICATION_ID, PackageManager.GET_SIGNATURES)
                .signatures
        if (signatures.isEmpty()) {
            return ""
        }
        val md = MessageDigest.getInstance("SHA-1")
        md.update(signatures.first().toByteArray())
        return md.digest().map { String.format("%02X", it) }.reduce { a, b -> "${a}${b}" }
    }
}

// https://www.googleapis.com/youtube/v3/search
interface Search {
    @GET("v3/search")
    suspend fun getList(
        @Query("key") key: String,
        @Query("part") part: String,
        @Query("type") type: String,
        @Query("q") keyword: String
    ): SearchResponse
}

data class SearchResponse(
    val kind: String,
    val etag: String,
    val nextPageToken: String,
    val regionCode: String,
    val pageInfo: PageInfo,
    val items: List<SearchListItem>
)

data class PageInfo(
    val totalResults: Int,
    val resultsPerPage: Int
)

data class SearchListItem(
    val kind: String,
    val etag: String,
    val id: ItemId,
    val snippet: Snippet
)

data class ItemId(
    val kind: String,
    val videoId: String
)

data class Snippet(
    val publishedAt: String,
    val channelId: String,
    val title: String,
    val description: String,
    val thumbnails: Thumbnails,
    val channelTitle: String,
    val liveBroadcastContent: String,
    val publishTime: String
)

data class Thumbnails(
    val default: Thumbnail,
    val medium: Thumbnail,
    val high: Thumbnail
)

data class Thumbnail(
    val url: String,
    val width: Int,
    val height: Int
)