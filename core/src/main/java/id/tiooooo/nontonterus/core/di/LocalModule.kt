package id.tiooooo.nontonterus.core.di

import androidx.room.Room
import id.tiooooo.nontonterus.core.constant.Constant.DATABASE_NAME
import id.tiooooo.nontonterus.core.local.NontonTerusDatabase
import id.tiooooo.nontonterus.core.local.datastore.AppDatastore
import org.koin.dsl.module

val localModule = module {
    single {
        Room.databaseBuilder(get(), NontonTerusDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration(false)
            .build()
    }
    single { get<NontonTerusDatabase>().searchHistoryDao() }
    single { AppDatastore(get()) }
}