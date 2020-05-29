package com.ragingo.ragitube.models.repos

import android.content.Context
import com.ragingo.ragitube.models.api.YouTubeApiClient
import com.ragingo.ragitube.models.db.AppDatabase
import com.ragingo.ragitube.models.db.entity.SearchHistory
import com.ragingo.ragitube.models.services.youtube.search.SearchResponse
import kotlinx.coroutines.*

class YouTubeRepository(
    private val context: Context
) {
    private val apiClient = YouTubeApiClient(context)

    suspend fun searchVideosByKeyword(keyword: String, maxCount: Int): SearchResponse {
        withContext(Dispatchers.IO) {
            AppDatabase.getInstance(context).searchHistoryDao().insert(SearchHistory(keyword, "dummy"))
        }
        return apiClient.searchByKeyword("snippet", "video", keyword, maxCount)
    }

    suspend fun getSearchHistories(): List<String> {
        return withContext(Dispatchers.IO) {
            return@withContext AppDatabase.getInstance(context).searchHistoryDao().getAll().map { x -> x.text }
        }
    }

}
