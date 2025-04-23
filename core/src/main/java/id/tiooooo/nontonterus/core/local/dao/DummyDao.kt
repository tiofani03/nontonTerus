package id.tiooooo.nontonterus.core.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.tiooooo.nontonterus.core.local.entity.DummyEntity

@Dao
interface DummyDao {
     @Insert(onConflict = OnConflictStrategy.REPLACE)
     suspend fun insertDummy(dummy: DummyEntity)

     @Query("SELECT * FROM dummy")
     suspend fun getAllDummies(): List<DummyEntity>

}