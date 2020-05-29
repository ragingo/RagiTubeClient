package com.ragingo.ragitube.models.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_history")
data class SearchHistory(
    @PrimaryKey(autoGenerate = false) val text: String,
    @ColumnInfo(name = "search_date") val searchDate: String
)
