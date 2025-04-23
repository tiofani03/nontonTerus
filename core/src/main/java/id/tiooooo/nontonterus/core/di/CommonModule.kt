package id.tiooooo.nontonterus.core.di

import id.tiooooo.nontonterus.core.common.DefaultDispatcher
import id.tiooooo.nontonterus.core.common.IoDispatcher
import id.tiooooo.nontonterus.core.common.MainDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val commonModule = module {
    single<CoroutineDispatcher>(qualifier = DefaultDispatcher) { Dispatchers.Default }
    single<CoroutineDispatcher>(qualifier = IoDispatcher) { Dispatchers.IO }
    single<CoroutineDispatcher>(qualifier = MainDispatcher) { Dispatchers.Main }
}