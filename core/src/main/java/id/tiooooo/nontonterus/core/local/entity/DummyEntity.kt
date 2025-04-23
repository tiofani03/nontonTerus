package id.tiooooo.nontonterus.core.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dummy")
data class DummyEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val uuid: String,
)
