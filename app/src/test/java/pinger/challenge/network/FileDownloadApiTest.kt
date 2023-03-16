package pinger.challenge.network

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations
import pinger.challenge.repository.PageSequenceRepository
import retrofit2.Response

class FileDownloadApiTest {

    private lateinit var pageSequenceRepository: PageSequenceRepository

    private val fileDownloadAPI: FileDownloadAPI = mockk()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun apacheFileIsReturned() = runBlocking {
        coEvery { fileDownloadAPI.downloadApacheLogStream() } returns Response.success(getResponse().toResponseBody())
        pageSequenceRepository = PageSequenceRepository(mockk(), fileDownloadAPI, mockk(), mockk())

        // val response = pageSequenceRepository.fetchMostPopularPathSequences(this).collect()

        // coVerify {}
    }

    private fun getResponse(): String {
        return "123.4.5.9 - - [03/Sep/2013:18:34:48 -0600] \"GET /team/ HTTP/1.1\" 200 3327 \"-\" \"Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:23.0) Gecko/20100101 Firefox/23.0\"\n" +
                "123.4.5.6 - - [03/Sep/2013:18:34:58 -0600] \"GET /products/car/ HTTP/1.1\" 200 3327 \"-\" \"Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:23.0) Gecko/20100101 Firefox/23.0\"\n" +
                "123.4.5.8 - - [03/Sep/2013:18:35:08 -0600] \"GET /products/desk/ HTTP/1.1\" 200 3327 \"-\" \"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.65 Safari/537.36\"\n" +
                "123.4.5.6 - - [03/Sep/2013:18:35:18 -0600] \"GET /products/desk/ HTTP/1.1\" 200 3327 \"-\" \"Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:23.0) Gecko/20100101 Firefox/23.0\"\n" +
                "123.4.5.9 - - [03/Sep/2013:18:35:28 -0600] \"GET /products/phone/ HTTP/1.1\" 200 3327 \"-\" \"Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:23.0) Gecko/20100101 Firefox/23.0\""
    }
}