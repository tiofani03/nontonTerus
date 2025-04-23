package id.tiooooo.nontonterus.core.di

import id.tiooooo.nontonterus.core.common.IoDispatcher
import id.tiooooo.nontonterus.core.constant.Constant
import id.tiooooo.nontonterus.core.network.helper.NetworkProvider
import org.koin.dsl.module
import retrofit2.Retrofit

val networkModule = module {
    single {
        NetworkProvider(
            context = get(),
            ioDispatcher = get(IoDispatcher),
            url = Constant.BASE_URL
        )
    }
    single<Retrofit> { get<NetworkProvider>().createRetrofit() }
}