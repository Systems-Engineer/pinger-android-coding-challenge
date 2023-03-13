package pinger.challenge.utility

import kotlinx.coroutines.flow.flow
import okio.BufferedSource
import java.io.IOException

object SourceReader {
    suspend fun getLinesOfInputFromSource(source: BufferedSource) = flow {
        try {
            while (!source.exhausted()) {
                val line = source.readUtf8Line()
                if (line != null) {
                    emit(ReaderProcessState.Next(line))
                }
            }
            emit(ReaderProcessState.Complete)
        } catch (ex: IOException) {
            ex.printStackTrace()
            emit(ReaderProcessState.Error(ex))
        }
    }
}

sealed class ReaderProcessState {
    object Complete: ReaderProcessState()
    data class Next(val data: String): ReaderProcessState()
    data class Error(val exception: Exception): ReaderProcessState()
}