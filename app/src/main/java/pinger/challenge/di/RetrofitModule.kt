package pinger.challenge.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import pinger.challenge.network.FileDownloadAPI
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    @Singleton
    @Provides
    fun provideOkhttpClient(): OkHttpClient {
        return OkHttpClient().newBuilder()
            .readTimeout(5, TimeUnit.SECONDS)
            .connectTimeout(5, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideApi(okHttpClient: OkHttpClient): FileDownloadAPI =
        Retrofit.Builder()
            .baseUrl("https://raw.githubusercontent.com")
            .client(okHttpClient)
            .build()
            .create(FileDownloadAPI::class.java)
}
