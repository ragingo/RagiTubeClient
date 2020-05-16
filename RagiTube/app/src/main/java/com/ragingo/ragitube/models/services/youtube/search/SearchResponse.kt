package com.ragingo.ragitube.models.services.youtube.search

data class SearchResponse(
    val kind: String,
    val etag: String,
    val nextPageToken: String,
    val regionCode: String,
    val pageInfo: PageInfo,
    val items: List<SearchListItem>
)
