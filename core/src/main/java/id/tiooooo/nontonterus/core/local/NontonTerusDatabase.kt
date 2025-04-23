package id.tiooooo.nontonterus.core.local

import androidx.room.Database
import androidx.room.RoomDatabase
import id.tiooooo.nontonterus.core.local.dao.DummyDao
import id.tiooooo.nontonterus.core.local.entity.DummyEntity

@Database(
    entities = [DummyEntity::class],
    version = 1,
    exportSchema = false
)
abstract class NontonTerusDatabase : RoomDatabase() {
    abstract fun dummyDao(): DummyDao
}