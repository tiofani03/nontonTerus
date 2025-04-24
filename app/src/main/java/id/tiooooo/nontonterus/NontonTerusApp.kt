package id.tiooooo.nontonterus

import android.app.Application
import id.tiooooo.nontonterus.core.di.commonModule
import id.tiooooo.nontonterus.core.di.localModule
import id.tiooooo.nontonterus.core.di.networkModule
import id.tiooooo.nontonterus.di.screenModelModule
import id.tiooooo.nontonterus.movie.di.movieModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class NontonTerusApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@NontonTerusApp)
            modules(
                listOf(
                    commonModule,
                    localModule,
                    networkModule,
                    movieModule,
                    screenModelModule,
                )
            )
        }
    }
}