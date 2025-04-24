package id.tiooooo.nontonterus.movie.di

import id.tiooooo.nontonterus.core.common.IoDispatcher
import id.tiooooo.nontonterus.movie.api.repository.MovieRepository
import id.tiooooo.nontonterus.movie.implementation.remote.api.MovieApi
import id.tiooooo.nontonterus.movie.implementation.repository.MovieRepositoryImpl
import org.koin.dsl.module
import retrofit2.Retrofit

val movieModule = module {
    single<MovieApi> { get<Retrofit>().create(MovieApi::class.java) }
    single<MovieRepository> { MovieRepositoryImpl(get(), get(), get(IoDispatcher)) }
}