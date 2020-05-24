package com.ragingo.ragitube.models.repos

import android.content.Context
import com.ragingo.ragitube.models.api.YouTubeApiClient
import com.ragingo.ragitube.models.services.youtube.search.SearchResponse

class YouTubeRepository(
    context: Context
) {
    private val apiClient = YouTubeApiClient(context)

    suspend fun searchVideosByKeyword(keyword: String, maxCount: Int): SearchResponse {
        return apiClient.searchByKeyword("snippet", "video", keyword, maxCount)
    }

}
