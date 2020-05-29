package com.ragingo.ragitube.models.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ragingo.ragitube.models.db.dao.SearchHistoryDao
import com.ragingo.ragitube.models.db.entity.SearchHistory

// 参考資料
// https://developer.android.com/training/data-storage/room?authuser=1&hl=ja

@Database(version = 1, exportSchema = true, entities = [
    SearchHistory::class
])
abstract class AppDatabase : RoomDatabase() {
    abstract fun searchHistoryDao(): SearchHistoryDao

    companion object {
        private var instance: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_db"
                ).build()
            }
            return instance as AppDatabase
        }
    }
}
