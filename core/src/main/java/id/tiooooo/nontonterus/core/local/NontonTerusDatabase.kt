package id.tiooooo.nontonterus.core.local

import androidx.room.Database
import androidx.room.RoomDatabase
import id.tiooooo.nontonterus.core.local.dao.SearchHistoryDao
import id.tiooooo.nontonterus.core.local.entity.SearchHistoryEntity

@Database(
    entities = [SearchHistoryEntity::class],
    version = 1,
    exportSchema = false
)
abstract class NontonTerusDatabase : RoomDatabase() {
    abstract fun searchHistoryDao(): SearchHistoryDao
}