package pinger.challenge.network

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET


interface FileDownloadAPI {

    @GET("/Systems-Engineer/pinger-android-coding-challenge/master/Apache.log")
    suspend fun downloadApacheLogStream(): Response<ResponseBody>
}