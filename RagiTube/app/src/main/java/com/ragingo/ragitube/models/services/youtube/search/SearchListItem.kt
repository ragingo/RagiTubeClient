package com.ragingo.ragitube.models.services.youtube.search

data class SearchListItem(
    val kind: String,
    val etag: String,
    val id: ItemId,
    val snippet: Snippet
)
