package com.ragingo.ragitube.models.services.youtube

import com.ragingo.ragitube.models.services.youtube.search.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

// https://www.googleapis.com/youtube/v3/search
interface YouTubeService {
    @GET("search")
    suspend fun searchByKeyword(
        @Query("key") key: String,
        @Query("part") part: String,
        @Query("type") type: String,
        @Query("q") keyword: String,
        @Query("maxResults") maxResults: Int,
        @Query("pageToken") pageToken: String? = null
    ): SearchResponse
}
