package pinger.challenge.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pinger.challenge.network.FileDownloadAPI
import pinger.challenge.parsing.ApacheLogParser
import pinger.challenge.repository.PageSequenceRepository
import pinger.challenge.utility.PageSequenceCalculator
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun providePageSequenceRepository(@ApplicationContext context: Context, api: FileDownloadAPI): PageSequenceRepository =
        PageSequenceRepository(context, api, ApacheLogParser(), PageSequenceCalculator())
}