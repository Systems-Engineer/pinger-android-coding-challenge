package pinger.challenge.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pinger.challenge.networking.FileDownloadAPI
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    @Singleton
    @Provides
    fun provideApi(): FileDownloadAPI =
        Retrofit.Builder()
            .baseUrl("https://raw.githubusercontent.com")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(FileDownloadAPI::class.java)
}
