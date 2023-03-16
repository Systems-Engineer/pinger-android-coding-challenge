package pinger.challenge.repository

import android.content.Context
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import pinger.challenge.R
import pinger.challenge.network.FileDownloadAPI
import pinger.challenge.parsing.ApacheLogParser
import pinger.challenge.intent.DataState
import pinger.challenge.utility.PageSequenceCalculator
import pinger.challenge.utility.ReaderProcessState
import pinger.challenge.utility.SourceReader
import javax.inject.Inject

class PageSequenceRepository @Inject constructor(
    private val context: Context,
    private val fileDownloadAPI: FileDownloadAPI,
    private val apacheLogParser: ApacheLogParser,
    private val pageSequenceCalculator: PageSequenceCalculator
) {
    private val numberOfConsecutivePages = 3

    suspend fun fetchMostPopularPathSequences() = channelFlow {
        val pathSequenceList = mutableListOf<String>()
        try {
            val response = fileDownloadAPI.downloadApacheLogStream()
            if (response.isSuccessful && response.body() != null) {
                SourceReader.getLinesOfInputFromSource(
                    response.body()!!.source()
                ).onEach {
                    when (it) {
                        is ReaderProcessState.Complete -> {
                            val mostCommonPageSequences = getMostCommonPageSequences(pathSequenceList)
                            send(DataState.Success(mostCommonPageSequences))
                            close()
                        }
                        is ReaderProcessState.Next -> {
                            pathSequenceList.add(it.data)
                        }
                        is ReaderProcessState.Error -> {
                            send(DataState.Error(it.exception))
                            close()
                        }
                    }
                }.collect()
            } else {
                send(DataState.Error(Exception(context.getString(R.string.request_failed))))
                close()
            }
        } catch (exc: Exception) {
            send(DataState.Error(Exception(context.getString(R.string.request_failed))))
            close()
        }
        awaitClose()
    }

    private fun getMostCommonPageSequences(pathSequenceData: MutableList<String>): MutableList<Pair<String, Int>> {
        val logs = apacheLogParser.parseLogsForEachUser(pathSequenceData)
        return pageSequenceCalculator.getMostCommonPageSequences(
            logs,
            numberOfConsecutivePages
        )
    }
}
