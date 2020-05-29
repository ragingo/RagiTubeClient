package com.ragingo.ragitube.models.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.ragingo.ragitube.models.db.entity.SearchHistory

@Dao
interface SearchHistoryDao {
    @Query("select * from search_history")
    fun getAll(): List<SearchHistory>

    @Insert(onConflict = REPLACE)
    fun insert(vararg items: SearchHistory)

    @Delete
    fun delete(item: SearchHistory)
}
