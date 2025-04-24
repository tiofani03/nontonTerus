package id.tiooooo.nontonterus.di

import id.tiooooo.nontonterus.pages.detail.MovieDetailScreenModel
import id.tiooooo.nontonterus.pages.home.MovieHomeScreenModel
import id.tiooooo.nontonterus.pages.list.MovieListScreenModel
import id.tiooooo.nontonterus.pages.review.ReviewScreenModel
import org.koin.dsl.module

val screenModelModule = module {
    factory { MovieHomeScreenModel(get()) }
    factory { MovieListScreenModel(get()) }
    factory { MovieDetailScreenModel(get()) }
    factory { ReviewScreenModel(get()) }
}