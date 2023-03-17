package pinger.challenge.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pinger.challenge.network.FileDownloadAPI
import pinger.challenge.parser.ApacheLogParser
import pinger.challenge.repository.PageSequenceRepository
import pinger.challenge.utility.PageSequenceCalculator
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun providePageSequenceRepository(api: FileDownloadAPI): PageSequenceRepository =
        PageSequenceRepository(api, ApacheLogParser(), PageSequenceCalculator())
}